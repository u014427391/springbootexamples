package com.example.ratelimit.strategy;


import com.example.ratelimit.core.RateLimitContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public abstract class AbstractRateLimitStrategy implements RateLimitStrategy {


    protected final StringRedisTemplate redisTemplate;

    protected AbstractRateLimitStrategy(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(RateLimitContext context) {
        String luaScript = loadLuaScript();
        Boolean pass = redisTemplate.execute(
                new DefaultRedisScript<>(luaScript, Boolean.class),
                Collections.singletonList(context.getCompositeKey()),
                buildArgs(context)
        );
        return Boolean.TRUE.equals(pass);
    }


    protected abstract String[] buildArgs(RateLimitContext ctx);

    protected abstract String luaFilePath();

    private String loadLuaScript() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(luaFilePath());
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read lua file: " + luaFilePath(), e);
        }
    }

}
