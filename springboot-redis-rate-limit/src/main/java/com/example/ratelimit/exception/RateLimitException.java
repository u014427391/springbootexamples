package com.example.ratelimit.exception;

public class RateLimitException extends RuntimeException {
    private final int code;

    public RateLimitException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}