package codeparser.db;

import java.sql.SQLException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class NullDBHandler implements DBHandler
{

	@Override
	public int register(String filePath) throws SQLException
	{
		return -1;	
	}

	@Override
	public void register(TypeDeclaration node) throws SQLException
	{
		// do nothing	
	}

}
