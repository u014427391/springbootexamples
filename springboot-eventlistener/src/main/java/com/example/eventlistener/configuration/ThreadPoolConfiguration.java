package com.example.eventlistener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfiguration {

    @Bean("asyncThreadPool")
    public Executor getAsyncThreadPool () {
        Executor executor = new ThreadPoolExecutor(
                20,30,60L, TimeUnit.SECONDS
                ,new ArrayBlockingQueue<>(100),new MyThreadFactory());
        return executor;
    }

    class MyThreadFactory implements ThreadFactory{
        final AtomicInteger threadNumber = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r){
            Thread t = new Thread(r);
            t.setName("thread-"+threadNumber.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }

}
