package ru.onlineshop.domain.exception;

@SuppressWarnings("serial")
public class NoOrderFoundException extends Exception {

	public NoOrderFoundException() {
		super();
	}

	public NoOrderFoundException(String message) {
		super(message);
	}

}
