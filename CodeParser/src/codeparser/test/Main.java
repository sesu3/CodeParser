package codeparser.test;

import java.io.IOException;
import java.sql.SQLException;

import codeparser.core.CodeParser;
import codeparser.db.DBHandler;
import codeparser.db.NullDBHandler;
import codeparser.db.ParserDBHandler;

public class Main
{
	public static void main(String[] args)
	{
		try{
			DBHandler dbh=new NullDBHandler();
			String sourcePath="/home/sho/research/repositories/eclipse-cs-git/net.sf.eclipsecs.core/src/net/sf/eclipsecs/core/CheckstylePlugin.java";
			//CodeParser.parsing(sourcePath,"/home/sho/research/testParse.txt",false);
			CodeParser.parsing(sourcePath,"/home/sho/research/OUT.txt",true,dbh);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("エラー終了しました");
		}/*catch(SQLException e){
			e.printStackTrace();
			System.out.println("エラー終了しました");
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
