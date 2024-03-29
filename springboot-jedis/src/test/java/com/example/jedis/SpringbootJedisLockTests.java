package com.example.jedis;

import cn.hutool.core.util.IdUtil;
import cn.hutool.cron.task.RunnableTask;
import com.example.jedis.common.JedisLockTemplate;
import com.example.jedis.configuration.RedisConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class SpringbootJedisLockTests {

    private static final String REDIS_KEY = "test:lock";

    @Autowired
    private JedisLockTemplate jedisLockTemplate;


    @Test
    public void testLock() throws InterruptedException {

//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        IntStream.range(0, 1).forEach(e->{
//            new Thread(new RunnableTask(countDownLatch)).start();
//        });
//        countDownLatch.await();

        for (int i = 0; i < 3; i++) {
            new Thread(()->redisLock()).start();
        }

//        redisLock();

    }

    class RedisLockThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            redisLock();
        }
    }



    class RunnableTask implements Runnable {

        CountDownLatch countDownLatch;

        public RunnableTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            redisLock();
            countDownLatch.countDown();
        }


    }

    private void redisLock() {
        String requestId = getRequestId();
        Boolean lock = jedisLockTemplate.acquire(REDIS_KEY, requestId, 5, 3);
        if (lock) {
            try {
                doSomeThing();
            } catch (Exception e) {
                jedisLockTemplate.release(REDIS_KEY, requestId);
            } finally {
                jedisLockTemplate.release(REDIS_KEY, requestId);
            }
        } else {
            log.warn("获取锁失败!");
        }
    }

    private void doSomeThing() throws InterruptedException {
        log.info("do some thing");
        Thread.sleep(15 * 1000);
    }

    private String getRequestId() {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<32;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }

}
