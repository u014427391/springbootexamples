package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("FIXED_WINDOW")
public class FixedWindowStrategy extends AbstractRateLimitStrategy {

    private static final String LUA ="";

    public FixedWindowStrategy(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String getLuaScript() {
        return LUA;
    }

    @Override
    protected String[] buildArgs(RateLimitContext ctx) {
        return new String[]{
                String.valueOf(ctx.getRate() ),
                String.valueOf(ctx.getLimit())
        };
    }
}
