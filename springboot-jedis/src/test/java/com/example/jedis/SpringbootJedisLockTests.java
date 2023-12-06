package com.example.jedis;

import cn.hutool.core.util.IdUtil;
import com.example.jedis.common.JedisLockTemplate;
import com.example.jedis.configuration.RedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
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

        IntStream.range(0, 1).forEach(e->{
            new Thread(new RunnableTask()).start();
        });

//        countDownLatch.await();
    }


    class RunnableTask implements Runnable {

//        CountDownLatch countDownLatch;

        public RunnableTask() {}

//        public RunnableTask (CountDownLatch countDownLatch) {
//            this.countDownLatch = countDownLatch;
//        }

        @Override
        public void run() {
            String requestId = IdUtil.simpleUUID();
            Boolean lock = jedisLockTemplate.lock(REDIS_KEY, requestId, 5, 5 * 1000);
            if (lock) {
                try {
                    doSomeThing();
                } catch (InterruptedException e) {
                    jedisLockTemplate.unlock(REDIS_KEY, requestId);
                } finally {
                    jedisLockTemplate.unlock(REDIS_KEY, requestId);
                }
            } else {
                log.warn("获取锁失败!");
            }
//            countDownLatch.countDown();
        }

        private void doSomeThing() throws InterruptedException {
            log.info("do some thing");
            Thread.sleep(15000);
        }
    }

}
