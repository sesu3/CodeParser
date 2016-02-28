package codeparser.core;

import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import codeparser.db.DBHandler;
import codeparser.db.NullDBHandler;
import codeparser.output.OutputIndicator;


class CodeVisitor extends ASTVisitor
{
	private OutputIndicator output;
	private DBHandler dbh;
	
	public CodeVisitor()
	{
		this.output=new OutputIndicator();
		this.dbh=new NullDBHandler();
	}
	public CodeVisitor(String outputFilePath) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,false);
		this.dbh=new NullDBHandler();
	}
	public CodeVisitor(String outputFilePath,boolean useStandard) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,useStandard);
		this.dbh=new NullDBHandler();
	}
	public CodeVisitor(String outputFilePath,boolean useStandard,DBHandler dbh) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,useStandard);
		this.dbh=dbh;
	}
	public CodeVisitor(boolean useStandard,DBHandler dbh) throws IOException
	{
		this.output=new OutputIndicator();
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
