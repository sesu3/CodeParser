package tmpj.db;

import java.sql.SQLException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tmpj.core.object.Revision;

public class NullDBHandler implements DBHandler
{
	
	@Override
	public void register(Revision rev) throws SQLException
	{
		// do nothing
	}
	
	@Override
	public void register(String hash, String filePath, String status) throws SQLException
	{
		// do nothing
	}

	@Override
	public void register(TypeDeclaration node) throws SQLException
	{
		// do nothing	
	}

}
