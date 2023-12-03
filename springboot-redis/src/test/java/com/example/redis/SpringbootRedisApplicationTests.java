package com.example.redis;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.redis.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest
class SpringbootRedisApplicationTests {

    private static final String REDIS_KEY = "testKeyRecord";

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        IntStream.range(0, 1000).forEach(e->{
            new Thread(new RunnableTest(countDownLatch)).start();
        });

        countDownLatch.await();
    }

    @Test
    void testZSetAdd() {

        IntStream.range(0, 1000).forEach(e->{
            invoke();
        });

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

        Long increment = getNextId();
        UserDto userDto = UserDto.builder()
                .id(increment)
                .name("user"+increment)
                .age(100)
                .email("123456@qq.com")
                .build();

        redisTemplate.opsForZSet().add(REDIS_KEY, JSONUtil.toJsonStr(userDto), increment);
        redisTemplate.expire(REDIS_KEY, getExpire(new Date()), TimeUnit.SECONDS);
    }

    private Long getNextId() {
        String idKey = String.format("testKeyId%s",  DateUtil.format(new Date() , DatePattern.PURE_DATE_PATTERN));
        Long increment = redisTemplate.opsForValue().increment(idKey);
        redisTemplate.expire(idKey, getExpire(new Date()), TimeUnit.SECONDS);
        return increment;
    }

    public static Long getExpire(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }


}
