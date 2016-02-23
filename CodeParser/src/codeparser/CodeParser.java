package codeparser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

class CodeParser
{
	private CompilationUnit unit;
	
	public CodeParser(String path) throws IOException
	{
		String sourceFile=new String(Files.readAllBytes(Paths.get(path)),StandardCharsets.UTF_8);
		ASTParser parser=ASTParser.newParser(AST.JLS8);
		parser.setSource(sourceFile.toCharArray());
		this.unit=(CompilationUnit)parser.createAST(new NullProgressMonitor());
	}
	
	public void extract()
	{
		CodeVisitor visitor=new CodeVisitor();
		unit.accept(visitor);
	}
}
