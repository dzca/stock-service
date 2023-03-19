package com.demo.stocks.exception;

public class CacheConnectionException extends Exception {

    public CacheConnectionException(String message) {
        super(message);
    }

    public CacheConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}