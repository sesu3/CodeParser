package tmpj.output;

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

import tmpj.core.ASTTool;
import tmpj.core.object.Variable;

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
		pw.printf("[%s]%n",ASTTool.getKind(node));
		pw.printf("name:%s%n",ASTTool.getFullyQualifiedClassName(node));
		pw.print("modifier:");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			String mod=iter.next();
			if(iter.hasNext()){
				pw.print(mod+",");
			}else{
				pw.print(mod);
			}
		}
		pw.println();
		pw.printf("range:%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		pw.printf("super:%s%n",ASTTool.getSuperclassType(node));
		pw.print("implements:");
		for(Iterator<Type> iter=node.superInterfaceTypes().iterator();iter.hasNext();){
			String type=iter.next().toString();
			if(iter.hasNext()){
				pw.print(type+",");
			}else{
				pw.print(type);
			}
		}
		pw.println();
		pw.print("field:");
		for(Iterator<Variable> iter=ASTTool.getFields(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			pw.print(ASTTool.getAccessModifier(v.getModifiers())+" ");
			for(Iterator<String> iter2=ASTTool.getModifiers(v.getModifiers()).iterator();iter2.hasNext();){
				pw.print(iter2.next()+" ");
			}
			if(iter.hasNext()){
				pw.print(v.getType()+" "+v.getName()+",");
			}else{
				pw.print(v.getType()+" "+v.getName());
			}
		}
		pw.println();
		pw.println();
		for(Iterator<MethodDeclaration> iter=Arrays.asList(node.getMethods()).iterator();iter.hasNext();){
			printDeclarationState(iter.next());
		}
	}

	public void printDeclarationState(MethodDeclaration node)
	{
		pw.printf("[%s]%n",ASTTool.getKind(node));
		pw.printf("name:%s%n",node.getName());
		pw.printf("returnType:%s%n",ASTTool.getReturnType(node));
		pw.print("modifier:");
		for(Iterator<String> iter=ASTTool.getModifiers(node).iterator();iter.hasNext();){
			String mod=iter.next();
			if(iter.hasNext()){
				pw.print(mod+",");
			}else{
				pw.print(mod);
			}
		}
		pw.println();
		pw.print("arguments:");
		for(Iterator<SingleVariableDeclaration> iter=node.parameters().iterator();iter.hasNext();){
			SingleVariableDeclaration argument=iter.next();
			if(iter.hasNext()){
				pw.print(argument.getType()+" "+argument.getName()+",");
			}else{
				pw.print(argument.getType()+" "+argument.getName());
			}
		}
		pw.println();
		pw.print("throws:");
		for(Iterator<Type> iter=node.thrownExceptionTypes().iterator();iter.hasNext();){
			String type=iter.next().toString();
			if(iter.hasNext()){
				pw.print(type+",");
			}else{
				pw.print(type);
			}
		}
		pw.println();
		pw.printf("range:%d,%d%n",ASTTool.getStartLineNumber(node),ASTTool.getEndLineNumber(node));
		pw.printf("location:%s%n",ASTTool.getFullyQualifiedClassName(node));
		pw.print("localVariable:");
		for(Iterator<Variable> iter=ASTTool.getLocalVariables(node).iterator();iter.hasNext();){
			Variable v=iter.next();
			if(iter.hasNext()){
				pw.print(v.getType()+" "+v.getName()+",");
			}else{
				pw.print(v.getType()+" "+v.getName());
			}
		}
		pw.println();
		pw.println();
	}
}
