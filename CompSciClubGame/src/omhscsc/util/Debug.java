package omhscsc.util;

public class Debug {

	
	/*
	 * Static class used for logging and stuff
	 */
	
	//May use files at some point
	
	public static void info(String s)
	{
		System.out.println("[I] " + s);
	}
	
	public static void warn(String s)
	{
		System.out.println("[-=!!!=-] " + s.toUpperCase());
	}
	
	//The point of this is to be able to place stack traces into Files
	//It's possible to e.printStackTrace(PrintStream s) but I've never used it before so I dunno
	public static String printStackTrace(Exception ex)
	{
		String exType = ex.toString();
		StackTraceElement[] st = ex.getStackTrace();
		String r = exType + "\n";
		for(StackTraceElement e : st)
		{
			r+="at " + e.toString() + "\n";
		}
		return r;
	}
	
	
}
