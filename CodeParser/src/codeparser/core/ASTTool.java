package codeparser.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import codeparser.core.object.Variable;

public class ASTTool
{
	
	public static int getStartLineNumber(BodyDeclaration node)
	{
		CompilationUnit unit=(CompilationUnit)node.getRoot();
		int docRange=0;
		Javadoc doc=node.getJavadoc();
		if(doc!=null){
			docRange=unit.getLineNumber(doc.getStartPosition()+doc.getLength())-unit.getLineNumber(doc.getStartPosition())+1;
		}
		return (unit.getLineNumber(node.getStartPosition())+docRange);
	}
	
	public static int getEndLineNumber(BodyDeclaration node)
	{
		CompilationUnit unit=(CompilationUnit)node.getRoot();
		return unit.getLineNumber(node.getStartPosition()+node.getLength());
	}
	
	public static String getFullyQualifiedClassName(ASTNode node)
	{
		if(node.getNodeType()==ASTNode.COMPILATION_UNIT){
			CompilationUnit cu=(CompilationUnit)node;
			PackageDeclaration pd=cu.getPackage();
			if(pd==null){
				return "";
			}
			return cu.getPackage().getName().getFullyQualifiedName();
		}else if(node.getNodeType()==ASTNode.TYPE_DECLARATION){
			String tmpName=getFullyQualifiedClassName(node.getParent());
			TypeDeclaration td=(TypeDeclaration)node;
			return tmpName+"."+td.getName().getFullyQualifiedName();
		}else{
			return getFullyQualifiedClassName(node.getParent());
		}
	}
	
	public static String getKind(TypeDeclaration node)
	{
		if(node.isInterface()){
			return "interface";
		}
		return "class";
	}
	
	public static String getKind(MethodDeclaration node)
	{
		if(node.isConstructor()){
			return "constructor";
		}
		return "method";
	}
	
	public static List<Variable> getFields(TypeDeclaration node)
	{
		List<Variable> list=new LinkedList<Variable>();
		for(Iterator<FieldDeclaration> iter=Arrays.asList(node.getFields()).iterator();iter.hasNext();){
			FieldDeclaration fd=iter.next();
			String variableType=fd.getType().toString();
			int modifiers=fd.getModifiers();
			for(Iterator<VariableDeclarationFragment> iter2=fd.fragments().iterator();iter2.hasNext();){
				VariableDeclarationFragment vdf=iter2.next();
				list.add(new Variable(modifiers,variableType,vdf.getName().toString()));
			}
		}
		return list;
	}
	
	public static List<Variable> getLocalVariables(MethodDeclaration node)
	{
		List<Variable> list=new LinkedList<Variable>();
		Block mb=node.getBody();
		if(mb!=null){
			for(Iterator<Statement> iter=mb.statements().iterator();iter.hasNext();){
				Statement st=iter.next();
				if(st instanceof ExpressionStatement){
					Expression exp=((ExpressionStatement) st).getExpression();
					if(exp instanceof Assignment){
						Expression leftExp=((Assignment) exp).getLeftHandSide();
						if(leftExp instanceof VariableDeclarationExpression){
							VariableDeclarationExpression vde=(VariableDeclarationExpression)leftExp;
							List<VariableDeclarationFragment> vdfList=vde.fragments();
							list.add(new Variable(vde.getType().toString(),vdfList.get(0).getName().toString()));
						}
					}
					if(exp instanceof VariableDeclarationExpression){
						VariableDeclarationExpression vde=(VariableDeclarationExpression)exp;
						String variableType=vde.getType().toString();
						for(Iterator<VariableDeclarationFragment> iter2=vde.fragments().iterator();iter2.hasNext();){
							VariableDeclarationFragment vdf=iter2.next();
							list.add(new Variable(variableType,vdf.getName().toString()));
						}
					}
				}
				if(st instanceof VariableDeclarationStatement){
					VariableDeclarationStatement vds=(VariableDeclarationStatement)st;
					String variableType=vds.getType().toString();
					for(Iterator<VariableDeclarationFragment> iter2=vds.fragments().iterator();iter2.hasNext();){
						VariableDeclarationFragment vdf=iter2.next();
						list.add(new Variable(variableType,vdf.getName().toString()));
					}
				}
			}
		}
		return list;
	}
	
	public static List<String> getModifiers(BodyDeclaration node)
	{
		int flags=node.getModifiers();
		List<String> list=new LinkedList<String>();
		if(Modifier.isAbstract(flags)){
			list.add("abstract");
		}
		if(Modifier.isDefault(flags)){
			list.add("default");
		}
		if(Modifier.isFinal(flags)){
			list.add("final");
		}
		if(Modifier.isNative(flags)){
			list.add("native");
		}
		if(Modifier.isPrivate(flags)){
			list.add("private");
		}
		if(Modifier.isProtected(flags)){
			list.add("protected");
		}
		if(Modifier.isPublic(flags)){
			list.add("public");
		}
		if(Modifier.isStatic(flags)){
			list.add("static");
		}
		if(Modifier.isStrictfp(flags)){
			list.add("strictfp");
		}
		if(Modifier.isSynchronized(flags)){
			list.add("synchronized");
		}
		if(Modifier.isTransient(flags)){
			list.add("transient");
		}
		if(Modifier.isVolatile(flags)){
			list.add("volatile");
		}
		return list;
	}
	
	public static String getSuperclassType(TypeDeclaration node)
	{
		Type type=node.getSuperclassType();
		if(type!=null){
			return type.toString();
		}
		return "null";
	}
	
	public static String getReturnType(MethodDeclaration node)
	{
		if(node.isConstructor()){
			return "null";
		}
		return node.getReturnType2().toString();
	}
	
	public static String getAccessModifier(BodyDeclaration node)
	{
		int flags=node.getModifiers();
		return getAccessModifier(flags);
	}
	
	public static String getAccessModifier(int flags)
	{
		if(Modifier.isPrivate(flags)){
			return "private";
		}else if(Modifier.isProtected(flags)){
			return "protected";
		}else if(Modifier.isPublic(flags)){
			return "public";
		}else{
			return "default";
		}
	}
	
}
