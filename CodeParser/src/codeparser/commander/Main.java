package codeparser.commander;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import codeparser.core.CodeParser;
import codeparser.db.DBHandler;
import codeparser.db.NullDBHandler;
import codeparser.db.ParserDBHandler;

public class Main
{

	public static void main(String[] args)
	{
		String path;
		String outfile=null;
		boolean visible=true;
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
				File file=new File(path);
				try {
					target=Main.getJavaFiles(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for(Iterator<String> iter=target.iterator();iter.hasNext();){
			try {
				if(outfile!=null){
					CodeParser.parsing(iter.next(),outfile,visible,dbh);
				}else{
					CodeParser.parsing(iter.next(),visible,dbh);
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
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
		String name=file.getName();
		int position=name.lastIndexOf(".");
		if(position==-1){
			return false;
		}else{
			String extension=name.substring(position);
			if(extension.equals(".java")){
				return true;
			}else{
				return false;
			}
		}
	}

}
