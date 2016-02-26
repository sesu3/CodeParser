package codeparser.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import codeparser.core.object.Variable;


class CodeVisitor extends ASTVisitor
{
	private static void printDeclarationRange(BodyDeclaration node)
	{
		System.out.println("range "+ASTTool.getStartLineNumber(node)+" "+ASTTool.getEndLineNumber(node));
	}
	
	private static void printModifiers(BodyDeclaration node)
	{
		System.out.print("modifier ");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			System.out.print(iter.next()+" ");
		}
		System.out.println();
	}
	
	private static void printTypeDeclarationState(TypeDeclaration node)
	{
		String nodeType=ASTTool.getKind(node);
		System.out.println(nodeType+" "+ASTTool.getFullyQualifiedClassName(node));
		printModifiers(node);
		printDeclarationRange(node);
		Type type=node.getSuperclassType();
		if(type!=null){
			System.out.println("parent "+type.toString());
		}
		System.out.print("implements ");
		for(Iterator<Type> iter=node.superInterfaceTypes().iterator();iter.hasNext();){
			System.out.print(iter.next().toString()+" ");
		}
		System.out.println();
		System.out.print("fields ");
		for(Iterator<Variable> iter=ASTTool.getFields(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			System.out.print("<"+v.getType()+" "+v.getName()+"> ");
		}
		System.out.println();
		System.out.println();
	}
	private static void printMethodDeclarationState(MethodDeclaration node)
	{
		String nodeType=ASTTool.getKind(node);
		System.out.println(nodeType+" "+node.getName());
		if(!node.isConstructor()){
			System.out.println("returnType "+node.getReturnType2());
		}
		printModifiers(node);
		System.out.print("arguments ");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			System.out.print(" <"+argument.getType()+" "+argument.getName()+">");
		}
		System.out.println();
		System.out.print("throws ");
		for(Iterator<Type> iter=node.thrownExceptionTypes().iterator();iter.hasNext();){
			System.out.print(iter.next().toString()+" ");
		}
		System.out.println();
		printDeclarationRange(node);
		System.out.println("declared "+ASTTool.getFullyQualifiedClassName(node));
		System.out.print("localVariable ");
		for(Iterator<Variable> iter=ASTTool.getLocalVariables(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			System.out.print("<"+v.getType()+" "+v.getName()+"> ");
		}
		System.out.println();
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
