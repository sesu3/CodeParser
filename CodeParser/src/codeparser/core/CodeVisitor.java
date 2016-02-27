package codeparser.core;

import java.io.IOException;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import codeparser.output.OutputIndicator;


class CodeVisitor extends ASTVisitor
{
	private OutputIndicator output;
	
	public CodeVisitor()
	{
		this.output=new OutputIndicator();
	}
	public CodeVisitor(String outputFilePath) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,false);
	}
	public CodeVisitor(String outputFilePath,boolean useStandard) throws IOException
	{
		this.output=new OutputIndicator(outputFilePath,useStandard);
	}
	
	public boolean visit(TypeDeclaration node)
	{
		output.printDeclarationState(node);
		return super.visit(node);
	}
	
	public boolean visit(MethodDeclaration node)
	{
		output.printDeclarationState(node);
		return super.visit(node);
	}
}
