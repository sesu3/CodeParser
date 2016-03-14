package tmpj.commander;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tmpj.core.CodeParser;
import tmpj.core.object.Revision;
import tmpj.db.DBHandler;

class ParseableUnitForDirectory extends ParseableUnit
{
	public ParseableUnitForDirectory(Option option)
	{
		super(option);
	}

	@Override
	public void execute()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		DBHandler dbh=option.getDBHandler();
		dbh.register(new Revision("1",null,null,null,null,null,null,null));
		List<String> target=getJavaFiles(new File(option.getPath()));
		for(Iterator<String> iter=target.iterator();iter.hasNext();){
			CodeParser.parsing(iter.next(),option);
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

}
