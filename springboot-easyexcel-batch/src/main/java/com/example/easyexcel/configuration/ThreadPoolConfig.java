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
        // 核心线程数（根据CPU核心数调整，避免过多线程竞争）
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);
        // 最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // 队列容量
        executor.setQueueCapacity(100);
        // 线程空闲时间（超过核心线程数的线程会被销毁）
        executor.setKeepAliveSeconds(60);
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