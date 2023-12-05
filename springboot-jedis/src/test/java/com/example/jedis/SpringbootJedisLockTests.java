package com.example.jedis;

import cn.hutool.core.util.IdUtil;
import com.example.jedis.common.JedisLockTemplate;
import com.example.jedis.configuration.RedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

    @Resource
    private JedisLockTemplate jedisLockTemplate;


    @Test
    public void testLock() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(100);

        IntStream.range(0, 100).forEach(e->{
            new Thread(new RunnableTask(countDownLatch)).start();
        });

        countDownLatch.await();
    }


    class RunnableTask implements Runnable {

        CountDownLatch countDownLatch;

        public RunnableTask (CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            String requestId = IdUtil.simpleUUID();
            try {
                jedisLockTemplate.lock(REDIS_KEY, requestId, 2, 2 * 1000);
                doSomeThing();
            } catch (InterruptedException e) {
                jedisLockTemplate.unlock(REDIS_KEY, requestId);
            } finally {
                jedisLockTemplate.unlock(REDIS_KEY, requestId);
            }
            countDownLatch.countDown();
        }

        private void doSomeThing() throws InterruptedException {
            log.info("do some thing");
            TimeUnit.MILLISECONDS.sleep(3000L);
        }
    }

}
