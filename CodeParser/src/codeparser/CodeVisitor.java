package codeparser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;


class CodeVisitor extends ASTVisitor
{
	public boolean visit(TypeDeclaration node)
	{
		CompilationUnit unit=(CompilationUnit)node.getRoot();
		Javadoc doc=node.getJavadoc();
		int docRange=unit.getLineNumber(doc.getStartPosition()+doc.getLength())-unit.getLineNumber(doc.getStartPosition())+1;
		String nodeType=node.isInterface()?"interface ":"class ";
		System.out.println(nodeType+node.getName());
		System.out.println("range "+(unit.getLineNumber(node.getStartPosition())+docRange)+" "
				+unit.getLineNumber(node.getStartPosition()+node.getLength()));
		System.out.println();
		return super.visit(node);
	}
	
	public boolean visit(MethodDeclaration node)
	{
		CompilationUnit unit=(CompilationUnit)node.getRoot();
		String nodeType=node.isConstructor()?"constructor ":"method ";
		System.out.println(nodeType+node.getName());
		System.out.println("returnType "+node.getReturnType2());
		System.out.println("arguments ");//Unimplemented
		System.out.println("range "+unit.getLineNumber(node.getBody().getStartPosition())+" "+unit.getLineNumber(node.getBody().getStartPosition()+node.getBody().getLength()));
		System.out.println();
		return super.visit(node);
	}
}
