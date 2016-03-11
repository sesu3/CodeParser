package codeparser.commander;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import codeparser.core.CodeParser;
import codeparser.core.object.Revision;
import codeparser.db.DBHandler;

class ParseableUnitForDirectory implements Parseable
{
	public ParseableUnitForDirectory(Option option)
	{
		this.option=option;
	}

	@Override
	public void execute()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		DBHandler dbh=option.getDBHandler();
		dbh.register(new Revision("1",null,null,null,null,null,null,null));
		List<String> target=getJavaFiles(new File(option.getPath()));
		for(Iterator<String> iter=target.iterator();iter.hasNext();){
			CodeParser.parsing(iter.next(),option.getOutfile(),option.getVisible(),dbh);
		}
	}
	
	private static List<String> getJavaFiles(File file) throws IOException
	{
		if(file.isDirectory()){
			List<String> list=new LinkedList<String>();
			for(Iterator<String> iter=Arrays.asList(file.list()).iterator();iter.hasNext();){
				list.addAll(getJavaFiles(new File(file.getAbsolutePath()+"/"+iter.next())));
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
	
	private Option option;

}
