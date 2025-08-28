package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 */
// 修改 AsyncConfig.java
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "batchInsertExecutor")
    public Executor batchInsertExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreThreads = Runtime.getRuntime().availableProcessors();

        executor.setCorePoolSize(coreThreads);
        executor.setMaxPoolSize(coreThreads * 2);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("BatchInsert-");
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}