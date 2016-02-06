package ru.onlineshop.domain.exception;

@SuppressWarnings("serial")
public class OpenOrderException extends Exception {

	public OpenOrderException() {
		super();
	}

	public OpenOrderException(String message) {
		super(message);
	}

}
