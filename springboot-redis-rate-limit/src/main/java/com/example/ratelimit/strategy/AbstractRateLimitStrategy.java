package com.example.ratelimit.strategy;


import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

public abstract class AbstractRateLimitStrategy implements RateLimitStrategy {


    protected final StringRedisTemplate redisTemplate;

    protected AbstractRateLimitStrategy(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(RateLimitContext context) {
        Boolean pass = redisTemplate.execute(
                new DefaultRedisScript<>(getLuaScript(), Boolean.class),
                Collections.singletonList(context.getCompositeKey()),
                buildArgs(context)
        );
        return Boolean.TRUE.equals(pass);
    }

    protected abstract String getLuaScript();

    protected abstract String[] buildArgs(RateLimitContext ctx);
}
