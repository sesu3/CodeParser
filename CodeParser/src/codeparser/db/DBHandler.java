package codeparser.db;

import java.sql.SQLException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import codeparser.core.object.Revision;

public interface DBHandler
{
	public void register(Revision rev) throws SQLException;
	public void register(String hash,String filePath,String status) throws SQLException;
	public void register(TypeDeclaration node) throws SQLException;
}
