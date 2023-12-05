package com.example.jedis.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    String lockKey();

    long expire(); // 毫秒

    int delayTime() default  0;



}
