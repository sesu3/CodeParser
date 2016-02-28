package codeparser.output;

import org.eclipse.jdt.core.dom.TypeDeclaration;

interface ParseWriter
{
	abstract public void printDeclarationState(TypeDeclaration node);
}
