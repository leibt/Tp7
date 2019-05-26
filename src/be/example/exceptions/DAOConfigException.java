package be.example.exceptions;

public class DAOConfigException extends RuntimeException{

	public DAOConfigException(String msg) {
		super(msg);
	}
	
	public DAOConfigException(String msg, Throwable cause) {
		super(msg,cause);
	}
	
	public DAOConfigException(Throwable cause) {
		super(cause);
	}
}
