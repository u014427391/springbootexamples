package com.example.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.redis.configuration.RedisConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
@ContextConfiguration(classes = RedisConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SpringbootRedisIncrTests {

    private static final String REDIS_ID_KEY = "testKey:id:%s";
    private static final String REDIS_LOCK_KEY = "testKey:lock";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @BeforeEach
    public void init(){
        log.info("init...");
        String idKey = String.format(REDIS_ID_KEY,  DateUtil.format(new Date() , DatePattern.PURE_DATE_PATTERN));
        redisTemplate.opsForValue().set(idKey, 1000);
    }

    @Test
    public void testIncr() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1024);
        IntStream.range(0, 1024).forEach(e->{
            new Thread(new RunnableTest(countDownLatch)).start();
        });

        countDownLatch.await();
    }

    class RunnableTest implements Runnable {

        CountDownLatch countDownLatch;

        public RunnableTest(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            invoke();
            countDownLatch.countDown();
        }
    }


    private void invoke() throws InterruptedException {
        RLock rLock = redissonClient.getLock(REDIS_LOCK_KEY);
        rLock.lock();
        try {
            String idKey = String.format(REDIS_ID_KEY, DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN));
            long incr = Convert.toLong(redisTemplate.opsForValue().get(idKey));
            if (incr > 0) {
                redisTemplate.opsForValue().decrement(idKey);
            }
            log.info("increment:{}", incr);
        } finally {
            rLock.unlock();
        }
    }




}
