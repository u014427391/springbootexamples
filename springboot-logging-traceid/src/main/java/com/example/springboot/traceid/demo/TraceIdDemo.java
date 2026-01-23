package com.example.springboot.traceid.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 演示traceId功能的示例类
 */
@Component
@Slf4j
public class TraceIdDemo implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Application started - Testing traceId functionality");
        
        // 演示MDC功能
        MDC.put("traceId", "DEMO-STARTUP-ID");
        log.info("This log has a demo traceId");
        MDC.clear();
        
        log.info("Application ready - Use /test/trace endpoint to test traceId in HTTP requests");
    }
}