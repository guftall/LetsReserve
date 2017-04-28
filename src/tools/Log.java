package tools;

public class Log {
	private static Log log;
	private StringBuilder logContent = new StringBuilder();
	
	public boolean enablePrinting = false;
	
	
	private Log() {
		
	}
	
	public static Log getLog() {
		if(log == null)
			return new Log();
		else
			return log;
	}
	
	public void addLog(String str) {
		if(enablePrinting)
			System.out.println(str);
		logContent.append(str + "\n");
	}
	
	public void printAllLog() {
		System.out.println(logContent.toString());
	}

}
