package codeparser.output;

import java.io.IOException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class OutputIndicator implements ParseWriter
{
	private ParseWriter standard;
	private ParseWriter file;
	
	public OutputIndicator()
	{
		this.standard=new StandardParseWriter();
		this.file=new NullParseWriter();
	}
	public OutputIndicator(String filePath,boolean useStandard) throws IOException
	{
		this.standard=useStandard?new StandardParseWriter():new NullParseWriter();
		this.file=new FileParseWriter(filePath);
	}

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		this.standard.printDeclarationState(node);
		this.file.printDeclarationState(node);
	}
	
}
