package codeparser.commander;

import java.sql.SQLException;

import codeparser.db.DBHandler;
import codeparser.db.NullDBHandler;
import codeparser.db.ParserDBHandler;

class Option
{
	public Option()
	{
		this.path=null;
		this.outfile=null;
		this.visible=true;
		this.target=TargetType.DIRECTORY;
		this.dbUser=null;
		this.dbPass=null;
		this.dbName=null;
		this.dbh=null;
	}
	
	public void setPath(String path)
	{
		this.path=path;
	}
	public void setOutfile(String outfile)
	{
		this.outfile=outfile;
	}
	public void setVisible(boolean visible)
	{
		this.visible=visible;
	}
	public void setTargetGit()
	{
		this.target=TargetType.GIT;
	}
	public void setDbUser(String dbUser)
	{
		this.dbUser=dbUser;
	}
	public void setDbPass(String dbPass)
	{
		this.dbPass=dbPass;
	}
	public void setDbName(String dbName)
	{
		this.dbName=dbName;
	}
	public String getPath()
	{
		return this.path;
	}
	public String getOutfile()
	{
		return this.outfile;
	}
	public boolean getVisible()
	{
		return this.visible;
	}
	public TargetType getTargetType()
	{
		return this.target;
	}
	public DBHandler getDBHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		if(dbh==null){
			if(dbUser!=null&&dbPass!=null&&dbName!=null){
				dbh=new ParserDBHandler(dbUser,dbPass,dbName);
			}else{
				dbh=new NullDBHandler();
			}
		}
		return dbh;
	}
	
	private String path;
	private String outfile;
	private boolean visible;
	private TargetType target;
	private String dbUser;
	private String dbPass;
	private String dbName;
	private DBHandler dbh;
	
	public enum TargetType
	{
		DIRECTORY,GIT;
	}
}
