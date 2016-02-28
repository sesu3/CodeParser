package codeparser.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import codeparser.core.ASTTool;
import codeparser.core.object.Variable;

class FileParseWriter implements ParseWriter
{
	private PrintWriter pw;

	public FileParseWriter(String filePath) throws IOException
	{
		this.pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath,true)),true);
	}

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		this.pw.printf("[kind]%n%s%n",ASTTool.getKind(node));
		this.pw.printf("[name]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		this.pw.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			this.pw.println(iter.next());
		}
		this.pw.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		this.pw.printf("[super]%n%s%n",ASTTool.getSuperclassType(node));
		this.pw.println("[implements]");
		for(Iterator<Type> iter=node.superInterfaceTypes().iterator();iter.hasNext();){
			this.pw.println(iter.next().toString());
		}
		this.pw.println("[fields]");
		for(Iterator<Variable> iter=ASTTool.getFields(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			for(Iterator<String> iter2=v.getModifiers().iterator();iter2.hasNext();){
				this.pw.print(iter2.next()+" ");
			}
			this.pw.println(v.getType()+" "+v.getName());
		}
		this.pw.println();
		for(Iterator<MethodDeclaration> iter=Arrays.asList(node.getMethods()).iterator();iter.hasNext();){
			printDeclarationState(iter.next());
		}
	}

	public void printDeclarationState(MethodDeclaration node)
	{
		this.pw.printf("[kind]%n%s%n",ASTTool.getKind(node));
		this.pw.printf("[name]%n%s%n",node.getName());
		this.pw.printf("[returnType]%n%s%n",ASTTool.getReturnType(node));
		this.pw.println("[modifier]");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			this.pw.println(iter.next());
		}
		this.pw.println("[arguments]");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			this.pw.println(argument.getType()+" "+argument.getName());
		}
		this.pw.println("[throws]");
		for(Iterator<Type> iter=node.thrownExceptionTypes().iterator();iter.hasNext();){
			this.pw.println(iter.next().toString());
		}
		this.pw.printf("[range]%n%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		this.pw.printf("[location]%n%s%n",ASTTool.getFullyQualifiedClassName(node));
		this.pw.println("[localVariable]");
		for(Iterator<Variable> iter=ASTTool.getLocalVariables(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			this.pw.println(v.getType()+" "+v.getName());
		}
		this.pw.println();
	}

	@Override
	protected void finalize() throws Throwable
	{
		try {
			super.finalize();
		} finally {
			if(this.pw!=null){
				this.pw.flush();
				this.pw.close();
			}
		}
	}

}
