package codeparser.core;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import codeparser.output.OutputIndicator;


class CodeVisitor extends ASTVisitor
{
	private OutputIndicator output;
	
	public CodeVisitor()
	{
		output=new OutputIndicator();
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
