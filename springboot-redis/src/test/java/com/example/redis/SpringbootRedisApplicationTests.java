package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100000);
        IntStream.range(0, 100000).forEach(e->{
            new Thread(new RunnableTest(countDownLatch)).start();
        });

        countDownLatch.await();
    }

    class RunnableTest implements Runnable {

        CountDownLatch countDownLatch;

        public RunnableTest(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            invoke();
            countDownLatch.countDown();
        }
    }


    private void invoke() {
        Long increment = redisTemplate.opsForValue().increment("testKey");
        System.out.println(increment);
    }

}
