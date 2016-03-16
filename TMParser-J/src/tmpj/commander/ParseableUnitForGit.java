package tmpj.commander;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import tmpj.core.CodeParser;
import tmpj.core.object.Revision;
import tmpj.db.DBHandler;

class ParseableUnitForGit extends ParseableUnit
{
	public ParseableUnitForGit(Option option)
	{
		super(option);
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
		String commitHash,an,ae,at,cn,ce,ct,b;
		commitHash=an=ae=at=cn=ce=ct=b="";
		StringBuilder sb=new StringBuilder("");
		while((line=br.readLine())!=null){
			if(0==count){
				String[] hashLine=line.split(",");
				commitHash=hashLine[1];
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
					dbh.register(new Revision(commitHash,an,ae,at,cn,ce,ct,b));
					parsingFilesForGit(commitHash,option);
					sb=new StringBuilder("");
					String[] hashLine=line.split(",");
					commitHash=hashLine[1];
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
		dbh.register(new Revision(commitHash,an,ae,at,cn,ce,ct,b));
		parsingFilesForGit(commitHash,option);
		proc.waitFor();
		br.close();
		proc.destroy();
	}
	
	private static void parsingFilesForGit(String hash,Option option)
			throws IOException, SQLException, InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		ProcessBuilder pb=new ProcessBuilder("git","log","-1","--pretty=format:","--name-status",hash);
		pb.directory(new File(option.getPath()));
		Process proc=pb.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		while((line=br.readLine())!=null){
			if(line.equals("")){
				continue;
			}
			String[] tmp=line.split("\t");
			if(isJavaFile(tmp[1])){
				if(tmp[0].equals("D")){
					CodeParser.parsing("".toCharArray(),tmp[1],hash,tmp[0],option);
				}else{
					CodeParser.parsing(getJavaFileFromGit(option.getPath(),hash,tmp[1]),tmp[1],hash,tmp[0],option);
				}
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
	
}
