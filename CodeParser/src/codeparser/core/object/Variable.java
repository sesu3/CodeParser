package codeparser.core.object;

import org.eclipse.jdt.core.dom.Modifier;

public class Variable
{
	private int modifiers;
	private String type;
	private String name;
	
	public Variable(String type,String name)
	{
		this.modifiers=Modifier.NONE;
		this.type=type;
		this.name=name;
	}
	public Variable(int modifiers,String type,String name)
	{
		this.modifiers=modifiers;
		this.type=type;
		this.name=name;
	}
	
	public int getModifiers()
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
