package codeparser.core.object;

public class Variable
{
	private String type;
	private String name;
	
	public Variable(String type,String name)
	{
		this.type=type;
		this.name=name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getName()
	{
		return this.name;
	}
}
