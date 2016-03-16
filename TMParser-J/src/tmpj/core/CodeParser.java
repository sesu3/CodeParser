package tmpj.core;

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

import tmpj.commander.Option;
import tmpj.db.DBHandler;

public class CodeParser
{

	public static void parsing(String sourcePath,Option option)
			throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(sourcePath)),StandardCharsets.UTF_8);
		CodeParser.parsing(sourceFile.toCharArray(),sourcePath,"1","-",option);
	}

	public static void parsing(char[] sourceFile,String sourceFileName,String hash,String status,Option option)
			throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		DBHandler dbh=option.getDBHandler();
		if(status.equals("D")){
			dbh.register(hash,sourceFileName,status);
			return;
		}
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		@SuppressWarnings("unchecked")
		Map<String, String> compilerOptions = JavaCore.getOptions();
		compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(compilerOptions);
		parser.setSource(sourceFile);
		CompilationUnit unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
		IProblem[] ip=unit.getProblems();
		boolean errorExist=false;
		for(Iterator<IProblem> iter=Arrays.asList(ip).iterator();iter.hasNext();){
			IProblem tmp=iter.next();
			if(tmp.isError()){
				//ロギング
				errorExist=true;
			}
			if(tmp.isWarning()){
				//ロギング
			}
		}
		if(errorExist){
			if(!option.getIgnoreErr()){
				dbh.register(hash,sourceFileName,"E");
			}
			return;
		}
		dbh.register(hash,sourceFileName,status);
		unit.accept(new CodeVisitor(option.getOutfile(),option.getVisible(),dbh));
	}

	public static String getProblemCategory(CategorizedProblem cp)
	{
		switch(cp.getCategoryID()){
		case CategorizedProblem.CAT_BUILDPATH:
			return "CAT_BUILDPATH";
		case CategorizedProblem.CAT_CODE_STYLE:
			return "CAT_CODE_STYLE";
		case CategorizedProblem.CAT_DEPRECATION:
			return "CAT_DEPRECATION";
		case CategorizedProblem.CAT_IMPORT:
			return "CAT_IMPORT";
		case CategorizedProblem.CAT_INTERNAL:
			return "CAT_INTERNAL";
		case CategorizedProblem.CAT_JAVADOC:
			return "CAT_JAVADOC";
		case CategorizedProblem.CAT_MEMBER:
			return "CAT_MEMBER";
		case CategorizedProblem.CAT_NAME_SHADOWING_CONFLICT:
			return "CAT_NAME_SHADOWING_CONFLICT";
		case CategorizedProblem.CAT_NLS:
			return "CAT_NLS";
		case CategorizedProblem.CAT_POTENTIAL_PROGRAMMING_PROBLEM:
			return "CAT_POTENTIAL_PROGRAMMING_PROBLEM";
		case CategorizedProblem.CAT_RESTRICTION:
			return "CAT_RESTRICTION";
		case CategorizedProblem.CAT_SYNTAX:
			return "CAT_SYNTAX";
		case CategorizedProblem.CAT_TYPE:
			return "CAT_TYPE";
		case CategorizedProblem.CAT_UNCHECKED_RAW:
			return "CAT_UNCHECKED_RAW";
		case CategorizedProblem.CAT_UNNECESSARY_CODE:
			return "CAT_UNNECESSARY_CODE";
		case CategorizedProblem.CAT_UNSPECIFIED:
			return "CAT_UNSPECIFIED";
		default:
			return "UNKNOWN";	
		}
	}

}
