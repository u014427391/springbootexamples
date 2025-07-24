package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("TOKEN_BUCKET")
public class TokenBucketStrategy extends AbstractRateLimitStrategy{

    private static final String LUA = "";

    public TokenBucketStrategy(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String getLuaScript() { return LUA; }

    @Override
    protected String[] buildArgs(RateLimitContext ctx) {
        return new String[]{
                String.valueOf(ctx.getLimit()),
                String.valueOf(ctx.getCapacity()),
                String.valueOf(System.currentTimeMillis()),
                "1"
        };
    }

}
