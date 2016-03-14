package tmpj.commander;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

abstract class ParseableUnit
{
	abstract public void execute()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException, InterruptedException;
	
	protected ParseableUnit(Option option)
	{
		this.option=option;
	}
	
	protected static boolean isJavaFile(File file)
	{
		return isJavaFile(file.getName());
	}
	
	protected static boolean isJavaFile(String fileName)
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
	
	protected Option option;
}
