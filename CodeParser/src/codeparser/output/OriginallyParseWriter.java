package codeparser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import codeparser.core.ASTTool;
import codeparser.core.object.Variable;

public class OriginallyParseWriter implements ParseWriter
{
	private PrintWriter pw;

	public OriginallyParseWriter(OutputStream os) throws IOException
	{
		this.pw=new PrintWriter(new OutputStreamWriter(os),true);
	}

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		pw.printf("[kind]%n%s%n",ASTTool.getKind(node));
		pw.printf("[name]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		pw.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			pw.println(iter.next());
		}
		pw.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		pw.printf("[super]%n%s%n",ASTTool.getSuperclassType(node));
		pw.println("[implements]");
		for(Iterator<Type> iter=node.superInterfaceTypes().iterator();iter.hasNext();){
			pw.println(iter.next().toString());
		}
		pw.println("[fields]");
		for(Iterator<Variable> iter=ASTTool.getFields(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			int flags=v.getModifiers();
			pw.print(ASTTool.getAccessModifier(flags)+" ");
			if(Modifier.isFinal(flags)){
				pw.print("final"+" ");
			}
			if(Modifier.isTransient(flags)){
				pw.print("transient"+" ");
			}
			if(Modifier.isVolatile(flags)){
				pw.print("volatile"+" ");
			}
			pw.println(v.getType()+" "+v.getName());
		}
		pw.println();
		for(Iterator<MethodDeclaration> iter=Arrays.asList(node.getMethods()).iterator();iter.hasNext();){
			printDeclarationState(iter.next());
		}
	}

	public void printDeclarationState(MethodDeclaration node)
	{
		pw.printf("[kind]%n%s%n",ASTTool.getKind(node));
		pw.printf("[name]%n%s%n",node.getName());
		pw.printf("[returnType]%n%s%n",ASTTool.getReturnType(node));
		pw.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			pw.println(iter.next());
		}
		this.pw.println("[arguments]");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			pw.println(argument.getType()+" "+argument.getName());
		}
		pw.println("[throws]");
		for(Iterator<Type> iter=node.thrownExceptionTypes().iterator();iter.hasNext();){
			pw.println(iter.next().toString());
		}
		pw.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		pw.printf("[location]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		pw.println("[localVariable]");
		for(Iterator<Variable> iter=ASTTool.getLocalVariables(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			pw.println(v.getType()+" "+v.getName());
		}
		pw.println();
	}
}
