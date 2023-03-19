package com.demo.stocks.exception;

public class StockFileUploadException extends Exception {

    public StockFileUploadException(String message) {
        super(message);
    }

    public StockFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}