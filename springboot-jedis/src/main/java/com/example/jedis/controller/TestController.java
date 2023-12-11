package com.example.jedis.controller;

import com.example.jedis.common.JedisLockTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {


    private static final String REDIS_KEY = "test:lock";

    @Autowired
    private JedisLockTemplate jedisLockTemplate;

    @GetMapping("test")
    public void test(@RequestParam("threadNum")Integer threadNum) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        IntStream.range(0, threadNum).forEach(e->{
            new Thread(new RunnableTask(countDownLatch)).start();
        });
        countDownLatch.await();

//        for (int i = 0; i < threadNum; i++) {
//            new Thread(()->redisLock()).start();
//        }
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
