package com.example.ratelimit.aop;

import com.example.ratelimit.core.RateLimitContext;
import com.example.ratelimit.keygenerator.KeyGenerator;
import com.example.ratelimit.core.RateLimiter;
import com.example.ratelimit.strategy.RateLimitStrategy;
import com.example.ratelimit.strategy.StrategyFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final StrategyFactory factory;
    private final ApplicationContext applicationContext;


    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint pjp, RateLimiter rateLimiter) throws Throwable {
        // 1. 生成主键
        KeyGenerator keyGen = applicationContext.getBean(rateLimiter.keyGen());
        String key = keyGen.generate();

        // 2. 选择策略
        RateLimitStrategy strategy = factory.get(rateLimiter.type());

        RateLimitContext ctx = RateLimitContext.builder()
                .compositeKey(key)
                .capacity(rateLimiter.capacity())
                .limit(rateLimiter.limit())
                .window(rateLimiter.window())
                .nowMillis(System.currentTimeMillis())
                .build();
        boolean pass = strategy.tryAcquire(ctx);
        if (!pass) {
            throw new IllegalArgumentException("Too many requests");
        }

        return pjp.proceed();
    }
}
