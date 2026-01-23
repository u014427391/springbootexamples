package com.example.springboot.traceid.controller;

import com.example.springboot.traceid.utils.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试Controller，用于验证traceId功能
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @GetMapping("/trace")
    public String testTraceId() {
        log.debug("This is a debug message with traceId");
        log.info("This is an info message with traceId");
        log.warn("This is a warn message with traceId");
        log.error("This is an error message with traceId");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        log.info("主线程日志 - traceId: {}", MdcUtil.getTraceId());
        String traceId = MdcUtil.getTraceId();

        // 解决方案：使用包装器包装任务
        executor.submit(() -> {
            MdcUtil.setTraceId(traceId);
            log.info("子线程日志 - traceId应该可见: {}", MdcUtil.getTraceId());

            // 嵌套任务也要设置traceId
            CompletableFuture.runAsync(() -> {
                MdcUtil.setTraceId(traceId);
                log.info("嵌套异步任务日志 - traceId应该可见: {}", MdcUtil.getTraceId());
            });
        });
        
        return "Check logs for traceId. All log messages should have traceId in the log format.";
    }
    
    @GetMapping("/hello")
    public String hello() {
        log.info("Hello endpoint called");
        return "Hello World! TraceId should be visible in logs.";
    }
    
    @GetMapping("/error")
    public String simulateError() {
        log.info("Simulating an error scenario");
        try {
            int result = 10 / 0;
            return "This should not be reached";
        } catch (Exception e) {
            log.error("Error occurred in simulateError endpoint", e);
            return "Error occurred, check logs for traceId";
        }
    }

}