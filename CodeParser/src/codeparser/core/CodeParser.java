package codeparser.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import codeparser.db.DBHandler;

public class CodeParser
{
	
	public static void parsing(String sourcePath) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor());
	}
	
	public static void parsing(String sourcePath,String outputFilePath) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(outputFilePath));
	}
	
	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(outputFilePath,useStandard));
	}
	
	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard,DBHandler dbh) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(outputFilePath,useStandard,dbh));
	}
	
	public static void parsing(String sourcePath,boolean useStandard,DBHandler dbh) throws IOException, SQLException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		dbh.register(sourcePath);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		unit.accept(new CodeVisitor(useStandard,dbh));
	}
}
