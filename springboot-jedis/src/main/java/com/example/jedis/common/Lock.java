package com.example.jedis.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    String lockKey();

    String requestId();

    int expire() default 30;

    int timeout() default  1;

}
