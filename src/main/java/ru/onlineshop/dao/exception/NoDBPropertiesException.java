package ru.onlineshop.dao.exception;

@SuppressWarnings("serial")
public class NoDBPropertiesException extends RuntimeException {

	public NoDBPropertiesException() {
		super();
	}

	public NoDBPropertiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDBPropertiesException(String message) {
		super(message);
	}

}
