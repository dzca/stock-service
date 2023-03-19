package com.demo.stocks.exception;

import io.lettuce.core.RedisConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    // =========== INTERNAL_SERVER_EXCEPTION (500) ==============

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<RestError> handleGeneralRestError(Exception ex, WebRequest request) {
        log.error("handleGeneralRestError, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.SERVER_ERROR);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { RedisConnectionException.class,  RedisConnectionFailureException.class})
    protected ResponseEntity<RestError> handleRedisConnectionException(Exception ex, WebRequest request) {
        log.error("handleRedisConnectionException, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.CACHE_ERROR);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { StockFileUploadException.class })
    protected ResponseEntity<RestError> handleStockFileUploadException(Exception ex, WebRequest request) {
        log.error("handleStockFileUploadException, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.FILE_UPLOAD_ERROR);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { CacheConnectionException.class })
    protected ResponseEntity<RestError> handleCacheConnectionException(Exception ex, WebRequest request) {
        log.error("handleCacheConnectionException, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.CACHE_ERROR);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // =========== BAD_REQUEST (400) ==============

    @ExceptionHandler(value = { InvalidAccountNameException.class })
    protected ResponseEntity<RestError> handleInvalidAccountNameException(Exception ex, WebRequest request) {
        log.error("handleInvalidAccountNameException, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.INVALID_ACCOUNT);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // =========== BAD_REQUEST (400) ==============

    @ExceptionHandler(value = { InvalidTokenException.class })
    protected ResponseEntity<RestError> handleInvalidTokenException(Exception ex, WebRequest request) {
        log.error("handleInvalidTokenException, err={}", ex.getLocalizedMessage());
        RestError err = new RestError();
        err.setErrorCode(ErrorCode.INVALID_TOKEN);
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }
}
