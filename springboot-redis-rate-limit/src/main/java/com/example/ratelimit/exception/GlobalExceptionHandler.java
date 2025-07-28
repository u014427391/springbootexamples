package com.example.ratelimit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ErrorResp> handleRateLimit(RateLimitException ex) {
        ErrorResp resp = new ErrorResp(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(resp);
    }

}

