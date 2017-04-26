package tools;

public class MyException extends Exception {

	public String msg;
	
	public MyException(String message) {
		this.msg = message;
	}
}
