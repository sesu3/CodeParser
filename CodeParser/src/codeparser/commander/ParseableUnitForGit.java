package codeparser.commander;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import codeparser.core.CodeParser;
import codeparser.core.object.Revision;
import codeparser.db.DBHandler;

class ParseableUnitForGit implements Parseable
{
	public ParseableUnitForGit(Option option)
	{
		this.option=option;
	}

	@Override
	public void execute()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		DBHandler dbh=option.getDBHandler();
		ProcessBuilder pb=new ProcessBuilder("git","log","--pretty=format:commit,%H%n%an%n%ae%n%at%n%cn%n%ce%n%ct%n%B","--reverse");
		pb.directory(new File(option.getPath()));
		Process proc=pb.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		int count=0;
		String h,an,ae,at,cn,ce,ct,b;
		h=an=ae=at=cn=ce=ct=b="";
		StringBuilder sb=new StringBuilder("");
		while((line=br.readLine())!=null){
			if(0==count){
				String[] hashLine=line.split(",");
				h=hashLine[1];
				line=br.readLine();
				count++;
			}
			an=line;
			ae=br.readLine();
			at=br.readLine();
			cn=br.readLine();
			ce=br.readLine();
			ct=br.readLine();
			while((line=br.readLine())!=null){
				if(line.matches("^commit,[0-9a-z]{40}+$")){
					b=sb.toString();
					dbh.register(new Revision(h,an,ae,at,cn,ce,ct,b));
					parsingFilesForGit(option.getPath(),h,option.getOutfile(),option.getVisible(),option.getIgnoreErr(),dbh);
					sb=new StringBuilder("");
					String[] hashLine=line.split(",");
					h=hashLine[1];
					count++;
					break;
				}else{
					if(!line.equals("")){
						sb.append(line);
						sb.append(System.getProperty("line.separator"));
					}
				}
			}
		}
		b=sb.toString();
		dbh.register(new Revision(h,an,ae,at,cn,ce,ct,b));
		parsingFilesForGit(option.getPath(),h,option.getOutfile(),option.getVisible(),option.getIgnoreErr(),dbh);
		proc.waitFor();
		br.close();
		proc.destroy();
	}
	
	private static void parsingFilesForGit(String dirPath,String hash,String outfile,boolean visible,boolean ignoreErr,DBHandler dbh)
			throws IOException, SQLException, InterruptedException
	{
		ProcessBuilder pb=new ProcessBuilder("git","log","-1","--pretty=format:","--name-status",hash);
		pb.directory(new File(dirPath));
		Process proc=pb.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		while((line=br.readLine())!=null){
			if(line.equals("")){
				continue;
			}
			String[] tmp=line.split("\t");
			if(isJavaFile(tmp[1])){
				CodeParser.parsing(getJavaFileFromGit(dirPath,hash,tmp[1]),tmp[1],hash,tmp[0],outfile,visible,ignoreErr,dbh);
			}
		}
		proc.waitFor();
		br.close();
		proc.destroy();
	}
	
	private static char[] getJavaFileFromGit(String dirPath,String hash,String filePath) throws IOException, InterruptedException
	{
		ProcessBuilder pb=new ProcessBuilder("git","show",hash+":"+filePath);
		pb.directory(new File(dirPath));
		Process proc=pb.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		StringBuilder sb=new StringBuilder("");
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}
		proc.waitFor();
		br.close();
		proc.destroy();
		return sb.toString().toCharArray();
	}
	
	private static boolean isJavaFile(String fileName)
	{
		int position=fileName.lastIndexOf(".");
		if(position==-1){
			return false;
		}else{
			String extension=fileName.substring(position);
			if(extension.equals(".java")){
				return true;
			}else{
				return false;
			}
		}
	}

	private Option option;

}
