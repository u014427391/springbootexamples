package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("SLIDING_WINDOW")
public class SlidingWindowStrategy extends AbstractRateLimitStrategy {

    public SlidingWindowStrategy(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String luaFilePath() {
        return "lua/sliding_window.lua";
    }

    @Override
    protected String[] buildArgs(RateLimitContext ctx) {
        long now = System.currentTimeMillis();
        return new String[]{
                String.valueOf(now),
                String.valueOf(ctx.getWindow() * 1000L),
                String.valueOf(ctx.getLimit())
        };
    }
}
