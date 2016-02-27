package codeparser.output;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import codeparser.core.ASTTool;
import codeparser.core.object.Variable;

class StandardParseWriter implements ParseWriter
{

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		System.out.printf("[kind]%n%s%n",ASTTool.getKind(node));
		System.out.printf("[name]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		System.out.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			System.out.println(iter.next());
		}
		System.out.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		System.out.printf("[parent]%n%s%n",ASTTool.getSuperclassType(node));
		System.out.println("[implements]");
		for(Iterator<Type> iter=node.superInterfaceTypes().iterator();iter.hasNext();){
			System.out.println(iter.next().toString());
		}
		System.out.println("[fields]");
		for(Iterator<Variable> iter=ASTTool.getFields(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			System.out.println(v.getType()+" "+v.getName());
		}
		System.out.println();
	}

	@Override
	public void printDeclarationState(MethodDeclaration node)
	{
		System.out.printf("[kind]%n%s%n",ASTTool.getKind(node));
		System.out.printf("[name]%n%s%n",node.getName());
		System.out.printf("[returnType]%n%s%n",ASTTool.getReturnType(node));
		System.out.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			System.out.println(iter.next());
		}
		System.out.println("[arguments]");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			System.out.println(argument.getType()+" "+argument.getName());
		}
		System.out.println("[throws]");
		for(Iterator<Type> iter=node.thrownExceptionTypes().iterator();iter.hasNext();){
			System.out.println(iter.next().toString());
		}
		System.out.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		System.out.printf("[location]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		System.out.println("[localVariable]");
		for(Iterator<Variable> iter=ASTTool.getLocalVariables(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			System.out.println(v.getType()+" "+v.getName());
		}
		System.out.println();
	}

}
