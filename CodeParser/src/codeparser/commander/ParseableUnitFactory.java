package codeparser.commander;

abstract class ParseableUnitFactory
{
	public static Parseable createUnit(Option option)
	{
		switch(option.getTargetType()){
		case DIRECTORY:
				return new ParseableUnitForDirectory(option);
		case GIT:
				return new ParseableUnitForGit(option);
		default:
				return new ParseableUnitForDirectory(option);
		}
	}
}
