package tmpj.db;

import java.sql.SQLException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tmpj.core.object.Revision;

public interface DBHandler
{
	public void register(Revision rev) throws SQLException;
	public void register(String filePath,String status) throws SQLException;
	public void register(TypeDeclaration node) throws SQLException;
}
