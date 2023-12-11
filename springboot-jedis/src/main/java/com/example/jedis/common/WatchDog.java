package com.example.jedis.common;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

@Component
@Aspect
@Slf4j
public class WatchDog {

    @Resource
    private JedisLockTemplate jedisLockTemplate;

    @Resource
    private ThreadPoolTaskExecutor executor;


    @Around("@annotation(Lock)")
    public Object proxy (ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Lock lock = method.getAnnotation(Lock.class);

        boolean acquire = jedisLockTemplate.acquire(lock.lockKey(), lock.requestId(), lock.expire(), lock.timeout());
        if (!acquire)
            throw new LockException("获取锁失败!");

        Future<Object> future = executor.submit(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                log.error("任务执行错误:{}", e);
                jedisLockTemplate.release(lock.lockKey(), lock.requestId());
                throw new RuntimeException("任务执行错误");
            } finally {
                jedisLockTemplate.release(lock.lockKey(), lock.requestId());
            }
        });

        return future.get();
    }


}
