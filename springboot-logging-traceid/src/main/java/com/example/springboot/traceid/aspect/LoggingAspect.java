package com.example.springboot.traceid.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * 日志切面，用于自动记录方法调用日志，包含traceId
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    @Around("execution(* com.example.springboot.traceid.controller.*.*(..)) || execution(* com.example.springboot.traceid.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String traceId = MDC.get("traceId");
        
        log.info("Method started - TraceId: {}, Class: {}, Method: {}", 
                traceId, className, methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("Method completed - TraceId: {}, Class: {}, Method: {}, ExecutionTime: {}ms", 
                    traceId, className, methodName, executionTime);
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.error("Method error - TraceId: {}, Class: {}, Method: {}, ExecutionTime: {}ms, Error: {}", 
                    traceId, className, methodName, executionTime, e.getMessage(), e);
            
            throw e;
        }
    }
}