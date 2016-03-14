package tmpj.output;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class OutputIndicator implements ParseWriter
{
	private ParseWriter standard;
	private ParseWriter file;
	
	public OutputIndicator(String filePath,boolean useStandard) throws IOException
	{
		this.standard=useStandard?new OriginallyParseWriter(System.out):new NullParseWriter();
		this.file=filePath!=null?new OriginallyParseWriter(new FileOutputStream(filePath,true)):new NullParseWriter();
	}

	@Override
	public void printDeclarationState(TypeDeclaration node)
	{
		this.standard.printDeclarationState(node);
		this.file.printDeclarationState(node);
	}
	
}
