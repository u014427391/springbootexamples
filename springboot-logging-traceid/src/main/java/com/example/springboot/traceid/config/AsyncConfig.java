package com.example.springboot.traceid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 异步任务配置
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    // 启用异步支持
    // 注意：@Async默认使用SimpleAsyncTaskExecutor，不支持MDC传递
    // 需要在@Async注解中指定自定义线程池：@Async("asyncTaskExecutor")
}