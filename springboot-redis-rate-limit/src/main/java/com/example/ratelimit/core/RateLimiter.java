package com.example.ratelimit.core;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    String key() default "rate:limit";

    long maxNum() default 10;

    long winWidth() default 1000;

    String message() default "";

}
