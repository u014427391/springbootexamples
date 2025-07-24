package com.example.ratelimit.strategy;

import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("LEAKY_BUCKET")
public class LeakyBucketStrategy extends AbstractRateLimitStrategy {

    private static final String LUA = "";


    public LeakyBucketStrategy(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String getLuaScript() { return LUA; }

    @Override
    protected String[] buildArgs(RateLimitContext ctx) {
        return new String[]{
                String.valueOf(ctx.getCapacity()),
                String.valueOf(ctx.getLimit()),   // 每毫秒流出量
                String.valueOf(System.currentTimeMillis())
        };
    }


}
