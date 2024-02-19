package com.example.delayqueue.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.delayqueue.core.Message;
import com.example.delayqueue.core.RedisDelayQueue;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class MessageConsumer implements InitializingBean {

    private ExecutorService executorService;
    
    @Autowired
    private RedisDelayQueue redisDelayQueue;

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = new ThreadPoolExecutor(
                20,30,60L, TimeUnit.SECONDS
                    ,new ArrayBlockingQueue<>(100),new MyThreadFactory());
    }

    @Scheduled(cron = "*/3 * * * * * ")
    public void consumer() {
        log.info("ready consumer...");
        executorService.execute(() -> {
            List<Message> messageList = Optional.ofNullable(redisDelayQueue.pull()).orElse(CollUtil.newArrayList());
            if (CollUtil.isNotEmpty(messageList)) {
                messageList.stream().forEach(message -> {
                    log.info("consumer {},consumer time:{}", JSONUtil.toJsonStr(message), DateUtil.now());
                    redisDelayQueue.remove(message);
                });
            }

        });
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
