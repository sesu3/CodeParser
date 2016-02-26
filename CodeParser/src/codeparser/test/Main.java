package codeparser.test;

import java.io.IOException;
import java.util.Scanner;

import codeparser.core.CodeParser;

public class Main
{
	public static void main(String[] args)
	{
		try{
			Scanner scan=new Scanner(System.in);
			System.out.print("please type javafile path: ");
			String sourcePath=scan.next();
			CodeParser.parsing(sourcePath);
			scan.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
