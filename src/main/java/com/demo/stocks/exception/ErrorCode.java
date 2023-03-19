package com.demo.stocks.exception;

public class ErrorCode {

    // 500
    public static final String SERVER_ERROR = "SERVER_ERROR";
    public static final String CACHE_ERROR = "CACHE_ERROR";
    public static final String FILE_UPLOAD_ERROR = "FILE_UPLOAD_ERROR";

    // 400
    public static final String INVALID_ACCOUNT = "INVALID_ACCOUNT";
    public static final String FILE_SIZE_EXCEED = "FILE_SIZE_EXCEED";

    // 401
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
}
