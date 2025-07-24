package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;

public interface RateLimitStrategy {
    boolean tryAcquire(RateLimitContext context);
}