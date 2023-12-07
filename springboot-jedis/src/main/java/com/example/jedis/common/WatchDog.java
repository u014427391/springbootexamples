package com.example.jedis.common;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class WatchDog {

    @Around("@annotation(Lock)")
    public Object proxy (ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }


}
