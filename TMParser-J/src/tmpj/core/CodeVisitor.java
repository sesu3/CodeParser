package tmpj.core;

import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tmpj.db.DBHandler;
import tmpj.output.OutputIndicator;


class CodeVisitor extends ASTVisitor
{
	private OutputIndicator output;
	private DBHandler dbh;
	
	public CodeVisitor(String outputFilePath,boolean useStandard,DBHandler dbh) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,useStandard);
		this.dbh=dbh;
	}
	
	public boolean visit(TypeDeclaration node)
	{
		this.output.printDeclarationState(node);
		try {
			this.dbh.register(node);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}
	
}
