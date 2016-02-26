package codeparser.output;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

class NullParseWriter implements ParseWriter
{

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		// do nothing	
	}

	@Override
	public void printDeclarationState(MethodDeclaration node)
	{
		// do nothing
	}

}
