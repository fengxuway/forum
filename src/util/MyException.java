package util;

public class MyException extends RuntimeException {
	private static final long serialVersionUID = 5764855096362656228L;
	String message;
	public MyException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	

}
