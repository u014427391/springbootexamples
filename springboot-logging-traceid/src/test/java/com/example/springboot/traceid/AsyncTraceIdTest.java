package com.example.springboot.traceid;

import com.example.springboot.traceid.utils.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步环境下traceId传递测试
 */
@SpringBootTest
@Slf4j
public class AsyncTraceIdTest {



    /**
     * 测试1：演示问题 - 普通线程池中traceId丢失
     */
    @Test
    public void testProblemWithoutMdcWrapper() throws Exception {
        log.info("=== 测试1：演示普通线程池中traceId丢失问题 ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // 在主线程设置traceId
        MdcUtil.setTraceId("test-trace-id-123");
        log.info("主线程日志 - traceId应该可见: {}", MdcUtil.getTraceId());
        
        // 问题：直接提交任务，traceId不会传递
        executor.submit(() -> {
            log.info("子线程日志 - traceId会丢失: {}", MdcUtil.getTraceId());
        }).get();
        
        executor.shutdown();
    }

    /**
     * 测试2：手动加traceId
     */
    @Test
    public void testWithMdcWrapper() throws Exception {
        log.info("=== 测试2：手动加traceId ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // 在主线程设置traceId
        MdcUtil.setTraceId("test-trace-id-456");
        log.info("主线程日志 - traceId: {}", MdcUtil.getTraceId());
        String traceId = MdcUtil.getTraceId();
        
        // 解决方案：使用包装器包装任务
        executor.submit(() -> {
            MdcUtil.setTraceId(traceId);
            log.info("子线程日志 - traceId应该可见: {}", MdcUtil.getTraceId());
            
            // 嵌套任务也会继承traceId
            CompletableFuture.runAsync(() -> {
                MdcUtil.setTraceId(traceId);
                log.info("嵌套异步任务日志 - traceId应该可见: {}", MdcUtil.getTraceId());
            });
        }).get();
        
        Thread.sleep(100); // 等待嵌套任务完成
        
        executor.shutdown();
    }

}