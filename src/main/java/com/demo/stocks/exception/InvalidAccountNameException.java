package com.demo.stocks.exception;

public class InvalidAccountNameException extends Exception {

	public InvalidAccountNameException(String message) {
        super(message);
    }

	public InvalidAccountNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
