package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("FIXED_WINDOW")
public class FixedWindowStrategy extends AbstractRateLimitStrategy {


    public FixedWindowStrategy(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String luaFilePath() {
        return "lua/fixed_window.lua";
    }

    @Override
    protected String[] buildArgs(RateLimitContext ctx) {
        return new String[]{
                String.valueOf(ctx.getWindow() ),
                String.valueOf(ctx.getLimit())
        };
    }
}
