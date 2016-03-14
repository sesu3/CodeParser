package tmpj.core.object;

public class Revision
{
	private String hash,authorName,authorMail,authorDate,committerName,committerMail,committerDate,message;
	public Revision(
			String hash,
			String authorName,String authorMail,String authorDate,
			String committerName,String committerMail,String committerDate,
			String message)
	{
		this.hash=hash;
		this.authorName=authorName;
		this.authorMail=authorMail;
		this.authorDate=authorDate;
		this.committerName=committerName;
		this.committerMail=committerMail;
		this.committerDate=committerDate;
		this.message=message;
	}
	
	public String getHash()
	{
		return this.hash;
	}
	public String getAuthorName()
	{
		return this.authorName;
	}
	public String getAuthorMail()
	{
		return this.authorMail;
	}
	public String getAuthorDate()
	{
		return this.authorDate;
	}
	public String getCommitterName()
	{
		return this.committerName;
	}
	public String getCommitterMail()
	{
		return this.committerMail;
	}
	public String getCommitterDate()
	{
		return this.committerDate;
	}
	public String getMessage()
	{
		return this.message;
	}
	
	public String toString()
	{
		StringBuilder sb=new StringBuilder("");
		sb.append("[hash] "+this.hash);
		sb.append(System.getProperty("line.separator"));
		sb.append("[name] "+this.authorName);
		sb.append(System.getProperty("line.separator"));
		sb.append("[mail] "+this.authorMail);
		sb.append(System.getProperty("line.separator"));
		sb.append("[date] "+this.authorDate);
		sb.append(System.getProperty("line.separator"));
		sb.append(this.message);
		return sb.toString();
	}
}
