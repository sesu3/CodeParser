package codeparser.core.object;

import java.util.List;

public class Variable
{
	private List<String> modifiers;
	private String type;
	private String name;
	
	public Variable(String type,String name)
	{
		this.modifiers=null;
		this.type=type;
		this.name=name;
	}
	public Variable(List<String> modifiers,String type,String name)
	{
		this.modifiers=modifiers;
		this.type=type;
		this.name=name;
	}
	
	public List<String> getModifiers()
	{
		return this.modifiers;
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
