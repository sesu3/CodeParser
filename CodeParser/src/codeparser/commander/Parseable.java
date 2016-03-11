package codeparser.commander;

import java.io.IOException;
import java.sql.SQLException;

public interface Parseable
{
	public void execute()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException, InterruptedException;
}
