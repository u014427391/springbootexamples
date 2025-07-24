package com.example.ratelimit.core;

import com.example.ratelimit.keygenerator.DefaultKeyGenerator;
import com.example.ratelimit.keygenerator.KeyGenerator;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流类型
     */
    RateLimitType type() default RateLimitType.FIXED_WINDOW;

    /**
     * 限流标识
     */
    Class<? extends KeyGenerator> keyGen() default DefaultKeyGenerator.class;

    /**
     * 桶容量
     */
    int capacity();

    /**
     * 阈值
     */
    int limit() default 10;

    /**
     * 窗口秒 or 速率
     */
    int rate() default 1;

}
