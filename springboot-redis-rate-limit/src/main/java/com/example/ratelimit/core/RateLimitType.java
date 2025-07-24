package com.example.ratelimit.core;

/**
 * 限流算法类型
 */
public enum RateLimitType {
    // 固定窗口限流
    FIXED_WINDOW,
    // 滑动窗口限流
    SLIDING_WINDOW,
    // 令牌桶限流
    TOKEN_BUCKET,
    // 漏桶限流
    LEAKY_BUCKET
}
    