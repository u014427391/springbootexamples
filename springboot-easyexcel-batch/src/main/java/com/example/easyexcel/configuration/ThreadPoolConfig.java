package com.example.easyexcel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {


    @Bean(name = "excelExecutor")
    public ThreadPoolTaskExecutor  excelExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(10);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程名称前缀
        executor.setThreadNamePrefix("excel-");
        // 当线程池达到最大线程数时如何处理新任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 允许回收核心线程
        executor.setAllowCoreThreadTimeOut(true);
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}