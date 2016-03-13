package codeparser.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import codeparser.db.DBHandler;

public class CodeParser
{

	public static void parsing(String sourcePath,String outputFilePath,boolean useStandard,boolean ignoreErr,DBHandler dbh) throws IOException, SQLException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		CodeParser.parsing(sourceFile.toCharArray(),sourcePath,"1","-",outputFilePath,useStandard,ignoreErr,dbh);
	}

	public static void parsing(char[] sourceFile,String sourceFileName,String hash,String status,String outputFilePath,boolean useStandard,boolean ignoreErr,DBHandler dbh)
			throws IOException, SQLException
	{
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		@SuppressWarnings("unchecked")
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(sourceFile);
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		IProblem[] ip=unit.getProblems();
		boolean errorExist=false;
		for(Iterator<IProblem> iter=Arrays.asList(ip).iterator();iter.hasNext();){
			IProblem tmp=iter.next();
			if(tmp.isError()){
				printProblem((CategorizedProblem)tmp);
				System.out.println(tmp.getMessage());
				System.out.println(hash+" "+sourceFileName+" でエラーを検知"+" "+unit.getLineNumber(tmp.getSourceStart())+" "+unit.getLineNumber(tmp.getSourceEnd()));
				errorExist=true;
			}
			if(tmp.isWarning()){
				printProblem((CategorizedProblem)tmp);
				System.out.println(tmp.getMessage());
				System.out.println(hash+" "+sourceFileName+" で警告を検知"+" "+unit.getLineNumber(tmp.getSourceStart())+" "+unit.getLineNumber(tmp.getSourceEnd()));
			}
		}
		if(errorExist&&ignoreErr){
			return;
		}
		dbh.register(hash,sourceFileName,status);
		unit.accept(new CodeVisitor(outputFilePath,useStandard,dbh));
	}

	public static void printProblem(CategorizedProblem cp)
	{
		switch(cp.getCategoryID()){
		case CategorizedProblem.CAT_BUILDPATH:
			System.out.println("CAT_BUILDPATH");
			break;
		case CategorizedProblem.CAT_CODE_STYLE:
			System.out.println("CAT_CODE_STYLE");
			break;
		case CategorizedProblem.CAT_DEPRECATION:
			System.out.println("CAT_DEPRECATION");
			break;
		case CategorizedProblem.CAT_IMPORT:
			System.out.println("CAT_IMPORT");
			break;
		case CategorizedProblem.CAT_INTERNAL:
			System.out.println("CAT_INTERNAL");
			break;
		case CategorizedProblem.CAT_JAVADOC:
			System.out.println("CAT_JAVADOC");
			break;
		case CategorizedProblem.CAT_MEMBER:
			System.out.println("CAT_MEMBER");
			break;
		case CategorizedProblem.CAT_NAME_SHADOWING_CONFLICT:
			System.out.println("CAT_NAME_SHADOWING_CONFLICT");
			break;
		case CategorizedProblem.CAT_NLS:
			System.out.println("CAT_NLS");
			break;
		case CategorizedProblem.CAT_POTENTIAL_PROGRAMMING_PROBLEM:
			System.out.println("CAT_POTENTIAL_PROGRAMMING_PROBLEM");
			break;
		case CategorizedProblem.CAT_RESTRICTION:
			System.out.println("CAT_RESTRICTION");
			break;
		case CategorizedProblem.CAT_SYNTAX:
			System.out.println("CAT_SYNTAX");
			break;
		case CategorizedProblem.CAT_TYPE:
			System.out.println("CAT_TYPE");
			break;
		case CategorizedProblem.CAT_UNCHECKED_RAW:
			System.out.println("CAT_UNCHECKED_RAW");
			break;
		case CategorizedProblem.CAT_UNNECESSARY_CODE:
			System.out.println("CAT_UNNECESSARY_CODE");
			break;
		case CategorizedProblem.CAT_UNSPECIFIED:
			System.out.println("CAT_UNSPECIFIED");
			break;
		default:
			System.out.println("UNKNOWN");	
		}
	}

}
