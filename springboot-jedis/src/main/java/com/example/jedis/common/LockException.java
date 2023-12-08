package com.example.jedis.common;

public class LockException extends RuntimeException{

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable t) {
        super(message, t);
    }

}
