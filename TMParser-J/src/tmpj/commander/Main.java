package tmpj.commander;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main
{

	public static void main(String[] args)
	{
		try {
			if(args.length==0){
				System.out.println("TYPE THE ESSENTIAL OPTIONS.");
				System.out.println("TMParser-J "+VERSION_INFO);
				System.exit(0);
			}
			ParseExecutable pe=new ParseExecutable();
			pe.setConfig(Arrays.asList(args));
			pe.execute();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static final String VERSION_INFO="v2.0";
	
}
