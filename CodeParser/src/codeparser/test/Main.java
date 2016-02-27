package codeparser.test;

import java.io.IOException;
import codeparser.core.CodeParser;

public class Main
{
	public static void main(String[] args)
	{
		try{
			String sourcePath="/home/sho/research/repositories/eclipse-cs-git/net.sf.eclipsecs.core/src/net/sf/eclipsecs/core/CheckstylePlugin.java";
			CodeParser.parsing(sourcePath,"/home/sho/research/testParse.txt",false);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("エラー終了しました");
		}
	}
}
