package codeparser;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		try{
			Scanner scan=new Scanner(System.in);
			System.out.print("please type javafile path: ");
			String path=scan.next();
			CodeParser parser=new CodeParser(path);
			parser.extract();
			scan.close();
		}catch(IOException e){
			System.out.println("error:");
			System.out.println(e.toString());
		}
	}
}
