package codeparser.db;

import java.sql.SQLException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public interface DBHandler
{
	public int register(String filePath) throws SQLException;
	public void register(TypeDeclaration node) throws SQLException;
}
