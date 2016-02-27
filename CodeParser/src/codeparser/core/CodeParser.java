package codeparser.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class CodeParser
{
	
	public static void parsing(String sourcePath) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor());
	}
	
	public static void parsing(String sourcePath,String outputFilePath) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(outputFilePath));
	}
	
	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(outputFilePath,useStandard));
	}
}
