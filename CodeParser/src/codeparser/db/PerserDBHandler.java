package codeparser.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PerserDBHandler
{
	private Connection connection;

	public PerserDBHandler(String server,String user,String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url="jdbc:mysql://"+server;
		this.connection=DriverManager.getConnection(url, user, password);
	}

	public boolean createDatabase(String dbName)
	{
		if(!dbName.matches("[a-zA-Z][a-zA-Z_]*")){
			return false;
		}
		try {
			Statement stmt = this.connection.createStatement();
			stmt.execute("create database "+dbName);
			stmt.execute("use "+dbName);
			//initialize database
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		try {
			super.finalize();
		} finally {
			if(this.connection!=null){
				this.connection.close();
			}
		}
	}
	
	private static final String CREATE_TABLE_FILE=
			"create table file(id integer primary key auto_increment,path text not null)";
	private static final String CREATE_TABLE_TYPE=
			"create table type(id integer primary key auto_increment,fileId integer not null,"+
			"isInterface boolean not null,FQCN text not null,startLine integer not null,"+
			"endLine integer not null,super text not null)";
	private static final String CREATE_TABLE_TDEXMODIFIER=
			"create table tdExModifier(typeId integer not null,keyword varchar(16) not null)";
	private static final String CREATE_TABLE_IMPLEMENTS=
			"create table implements(typeId integer not null,name text not null)";
	private static final String CREATE_TABLE_FIELD=
			"create table field(typeId integer not null,exmod varchar(16) not null,type text not null,name text not null)";
	private static final String CREATE_TABLE_METHOD=
			"create table method(id integer primary key auto_increment,location text not null,"+
			"isConstructor boolean not null,name text not null,returnType text not null,"+
			"startLine integer not null,endLine integer not null)";
	private static final String CREATE_TABLE_MDEXMODIFIER=
			"create table mdExModifier(methodId integer not null,keyword varchar(16) not null)";
	private static final String CREATE_TABLE_THROWS=
			"create table throws(methodId integer not null,type text not null)";
	private static final String CREATE_TABLE_ARGUMENT=
			"create table argument(methodId integer not null,type text not null,name text not null)";
	private static final String CREATE_TABLE_VARIABLE=
			"create table variable(methodId integer not null,type text not null,name text not null)";

	public static void main(String[] args)
	{

		try {
			PerserDBHandler pdbh=new PerserDBHandler("localhost","root","6700");
			System.out.println("ドライバのロードに成功");
			System.out.println(pdbh.createDatabase("eclipse"));
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
