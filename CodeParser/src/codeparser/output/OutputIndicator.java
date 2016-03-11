package codeparser.output;

import java.io.IOException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class OutputIndicator implements ParseWriter
{
	private ParseWriter standard;
	private ParseWriter file;
	
	public OutputIndicator(String filePath,boolean useStandard) throws IOException
	{
		this.standard=useStandard?new StandardParseWriter():new NullParseWriter();
		this.file=filePath!=null?new FileParseWriter(filePath):new NullParseWriter();
	}

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		this.standard.printDeclarationState(node);
		this.file.printDeclarationState(node);
	}
	
}
