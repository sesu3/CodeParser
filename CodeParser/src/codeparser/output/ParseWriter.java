package codeparser.output;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

interface ParseWriter
{
	abstract public void printDeclarationState(TypeDeclaration node);
	abstract public void printDeclarationState(MethodDeclaration node);
}
