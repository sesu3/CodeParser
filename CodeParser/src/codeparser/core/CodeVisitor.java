package codeparser.core;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;


class CodeVisitor extends ASTVisitor
{
	private static void printDeclarationRange(BodyDeclaration node)
	{
		CompilationUnit unit=(CompilationUnit)node.getRoot();
		int docRange=0;
		Javadoc doc=node.getJavadoc();
		if(doc!=null){
			docRange=unit.getLineNumber(doc.getStartPosition()+doc.getLength())-unit.getLineNumber(doc.getStartPosition())+1;
		}
		System.out.println("range "+(unit.getLineNumber(node.getStartPosition())+docRange)+" "
				+unit.getLineNumber(node.getStartPosition()+node.getLength()));
	}
	private static void printTypeDeclarationState(TypeDeclaration node)
	{
		String nodeType=node.isInterface()?"interface ":"class ";
		System.out.println(nodeType+node.getName());
		printDeclarationRange(node);
		System.out.println();
	}
	private static void printMethodDeclarationState(MethodDeclaration node)
	{
		String nodeType=node.isConstructor()?"constructor ":"method ";
		System.out.println(nodeType+node.getName());
		if(!node.isConstructor()){
			System.out.println("returnType "+node.getReturnType2());
		}
		System.out.print("arguments ");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			System.out.print(" <"+argument.getType()+" "+argument.getName()+">");
		}
		System.out.println();
		printDeclarationRange(node);
		System.out.println();
	}
	public boolean visit(TypeDeclaration node)
	{
		printTypeDeclarationState(node);
		return super.visit(node);
	}
	
	public boolean visit(MethodDeclaration node)
	{
		printMethodDeclarationState(node);
		return super.visit(node);
	}
}
