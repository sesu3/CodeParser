package tmpj.commander;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class ParseExecutable
{
	public ParseExecutable()
	{
		this.option=new Option();
	}

	public void setConfig(List<String> arguments)
	{
		for(Iterator<String> iter=arguments.iterator();iter.hasNext();){
			String input=iter.next();
			if(input.startsWith("-")){
				if(input.equals("-outfile")){
					option.setOutfile(iter.next());
				}
				if(input.equals("-visible")){
					option.setVisible(true);
				}
				if(input.equals("-invisible")){
					option.setVisible(false);
				}
				if(input.equals("-ignore-err")){
					option.setIgnoreErr();
				}
				if(input.equals("-git")){
					option.setTargetGit();
				}
				if(input.equals("-dbuser")){
					option.setDbUser(iter.next());
				}
				if(input.equals("-dbpw")){
					option.setDbPass(iter.next());
				}
				if(input.equals("-dbname")){
					option.setDbName(iter.next());
				}
			}else{
				option.setPath(input);
			}
		}
	}
	
	public void execute() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		ParseableUnit p=ParseableUnitFactory.createUnit(option);
		p.execute();
	}

	private Option option;
}
