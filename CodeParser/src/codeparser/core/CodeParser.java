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
import codeparser.db.NullDBHandler;

public class CodeParser
{
	
	public static void parsing(String sourcePath) throws IOException
	{
		CodeParser.parsing(sourcePath,null,true,new NullDBHandler());
	}
	
	public static void parsing(String sourcePath,String outputFilePath) throws IOException
	{
		CodeParser.parsing(sourcePath,outputFilePath,true,new NullDBHandler());
	}
	
	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard) throws IOException
	{
		CodeParser.parsing(sourcePath,outputFilePath,useStandard,new NullDBHandler());
	}
	
	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard,DBHandler dbh) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		@SuppressWarnings("unchecked")
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile.toCharArray());
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		if(outputFilePath==null){
			unit.accept(new CodeVisitor(useStandard,dbh));
		}else{
			unit.accept(new CodeVisitor(outputFilePath,useStandard,dbh));
		}
	}
	
	public static void parsing(String sourcePath,boolean useStandard,DBHandler dbh) throws IOException, SQLException
	{
		CodeParser.parsing(sourcePath,null,useStandard,dbh);
	}
}
