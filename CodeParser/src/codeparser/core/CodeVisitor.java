package codeparser.core;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
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
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


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
	private static void printModifiers(int flags)
	{
		String modifiers="modifier ";
		modifiers+=Modifier.isAbstract(flags)?"abstract ":"";
		modifiers+=Modifier.isDefault(flags)?"default ":"";
		modifiers+=Modifier.isFinal(flags)?"final ":"";
		modifiers+=Modifier.isNative(flags)?"native ":"";
		modifiers+=Modifier.isPrivate(flags)?"private ":"";
		modifiers+=Modifier.isProtected(flags)?"protected ":"";
		modifiers+=Modifier.isPublic(flags)?"public ":"";
		modifiers+=Modifier.isStatic(flags)?"static ":"";
		modifiers+=Modifier.isStrictfp(flags)?"strictfp ":"";
		modifiers+=Modifier.isSynchronized(flags)?"synchronized ":"";
		modifiers+=Modifier.isTransient(flags)?"transient ":"";
		modifiers+=Modifier.isVolatile(flags)?"volatile ":"";
		System.out.println(modifiers);
	}
	private static String getFullyQualifiedClassName(ASTNode node)
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
	private static void printTypeDeclarationState(TypeDeclaration node)
	{
		String nodeType=node.isInterface()?"interface ":"class ";
		System.out.println(nodeType+getFullyQualifiedClassName(node));
		printModifiers(node.getModifiers());
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
		for(Iterator<FieldDeclaration> iter=Arrays.asList(node.getFields()).iterator();iter.hasNext();){
			FieldDeclaration fd=iter.next();
			System.out.print("<"+fd.getType().toString()+" ");
			int cnt=0;
			for(Iterator<VariableDeclarationFragment> iter2=fd.fragments().iterator();iter2.hasNext();cnt++){
				VariableDeclarationFragment vdf=iter2.next();
				if(cnt==0){
					System.out.print(vdf.getName());
				}else{
					System.out.print(", "+vdf.getName());
				}
			}
			System.out.print(">");
		}
		System.out.println();
		System.out.println();
	}
	private static void printMethodDeclarationState(MethodDeclaration node)
	{
		String nodeType=node.isConstructor()?"constructor ":"method ";
		System.out.println(nodeType+node.getName());
		if(!node.isConstructor()){
			System.out.println("returnType "+node.getReturnType2());
		}
		printModifiers(node.getModifiers());
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
		System.out.println("declared "+getFullyQualifiedClassName(node));
		System.out.print("localVariable ");
		Block mb=node.getBody();
		if(mb!=null){
			for(Iterator<Statement> iter=mb.statements().iterator();iter.hasNext();){
				Statement st=iter.next();
				if(st instanceof ExpressionStatement){
					if(st instanceof Assignment){
						// unimplemented
					}
					if(st instanceof VariableDeclarationExpression){
						VariableDeclarationExpression vde=(VariableDeclarationExpression)st;
						System.out.print("<"+vde.getType().toString()+" ");
						int cnt=0;
						for(Iterator<VariableDeclarationFragment> iter2=vde.fragments().iterator();iter2.hasNext();cnt++){
							VariableDeclarationFragment vdf=iter2.next();
							if(cnt==0){
								System.out.print(vdf.getName());
							}else{
								System.out.print(", "+vdf.getName());
							}
						}
					}
				}
				if(st instanceof VariableDeclarationStatement){
					VariableDeclarationStatement vds=(VariableDeclarationStatement)st;
					System.out.print("<"+vds.getType().toString()+" ");
					int cnt=0;
					for(Iterator<VariableDeclarationFragment> iter2=vds.fragments().iterator();iter2.hasNext();cnt++){
						VariableDeclarationFragment vdf=iter2.next();
						if(cnt==0){
							System.out.print(vdf.getName());
						}else{
							System.out.print(", "+vdf.getName());
						}
					}
				}
			}
		}
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
