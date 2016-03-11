package codeparser.commander;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import codeparser.core.CodeParser;
import codeparser.core.object.Revision;
import codeparser.db.DBHandler;
import codeparser.db.NullDBHandler;
import codeparser.db.ParserDBHandler;

public class Main
{

	public static void main(String[] args)
	{
		String path="";
		String outfile=null;
		boolean visible=true;
		boolean git=false;
		DBHandler dbh=new NullDBHandler();
		List<String> target=new LinkedList<String>();
		for(Iterator<String> iter=Arrays.asList(args).iterator();iter.hasNext();){
			String argument=iter.next();
			if(argument.startsWith("-")){
				if(argument.equals("-outfile")){
					outfile=iter.next();
				}
				if(argument.equals("-visible")){
					visible=Boolean.parseBoolean(iter.next());
				}
				if(argument.equals("-git")){
					git=true;
				}
				if(argument.equals("-db")){
					String tmp=iter.next();
					String server=null;
					String user=null;
					String password=null;
					String dbname=null;
					for(Iterator<String> iter2=Arrays.asList(tmp.split(",")).iterator();iter2.hasNext();){
						String tmp2[]=iter2.next().split(":");
						if(tmp2[0].equals("server")){
							server=tmp2[1];
						}
						if(tmp2[0].equals("user")){
							user=tmp2[1];
						}
						if(tmp2[0].equals("password")){
							password=tmp2[1];
						}
						if(tmp2[0].equals("dbname")){
							dbname=tmp2[1];
						}
					}
					try {
						dbh=new ParserDBHandler(server,user,password,dbname);
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e) {
						e.printStackTrace();
					}
				}
			}else{
				path=argument;
			}
		}
		if(git){
			try {
				ProcessBuilder pb=new ProcessBuilder("git","log","--pretty=format:commit,%H%n%an%n%ae%n%at%n%cn%n%ce%n%ct%n%B","--reverse");
				pb.directory(new File(path));
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
							parsingFilesForGit(path,h,outfile,visible,dbh);
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
				parsingFilesForGit(path,h,outfile,visible,dbh);
				proc.waitFor();
				br.close();
				proc.destroy();
			} catch (IOException | InterruptedException | SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			try {
				dbh.register(new Revision("1",null,null,null,null,null,null,null));
				target=Main.getJavaFiles(new File(path));
				for(Iterator<String> iter=target.iterator();iter.hasNext();){
					CodeParser.parsing(iter.next(),outfile,visible,dbh);
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void parsingFilesForGit(String dirPath,String hash,String outfile,boolean visible,DBHandler dbh)
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
				CodeParser.parsing(getJavaFileFromGit(dirPath,hash,tmp[1]),tmp[1],hash,tmp[0],outfile,visible,dbh);
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

	private static List<String> getJavaFiles(File file) throws IOException
	{
		if(file.isDirectory()){
			List<String> list=new LinkedList<String>();
			for(Iterator<String> iter=Arrays.asList(file.list()).iterator();iter.hasNext();){
				list.addAll(Main.getJavaFiles(new File(file.getAbsolutePath()+"/"+iter.next())));
			}
			return list;
		}else{
			List<String> list=new LinkedList<String>();
			if(file.isFile()&&isJavaFile(file)){
				list.add(file.getAbsolutePath());
			}
			return list;
		}
	}

	private static boolean isJavaFile(File file)
	{
		return isJavaFile(file.getName());
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

}
