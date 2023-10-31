package com.example.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class SpringbootHutoolApplicationTests {

    @Test
    void contextLoads() {
        TimeInterval timeInterval = DateUtil.timer();
        AtomicInteger num1 = new AtomicInteger(1);
        IntStream.range(1,10).forEach(i-> num1.incrementAndGet());
        log.info("耗时: {} s" , timeInterval.intervalRestart() / 1000);
        log.info("num: {}" , num1);

        AtomicInteger num2 = new AtomicInteger(1);
        IntStream.range(1,100).forEach(i-> num2.incrementAndGet());
        log.info("耗时: {} s" , timeInterval.intervalRestart() / 1000);
        log.info("num: {}" , num2);

    }

    @Test
    void testNeedTime() {
        long startTime = System.currentTimeMillis();

        AtomicInteger num1 = new AtomicInteger(1);
        IntStream.range(1,10).forEach(i-> num1.incrementAndGet());
        long endTime1 = System.currentTimeMillis();
        log.info("耗时: {}" , endTime1 - startTime);

        AtomicInteger num2 = new AtomicInteger(1);
        IntStream.range(1,100).forEach(i-> num2.incrementAndGet());
        long endTime2 = System.currentTimeMillis();
        log.info("耗时: {}" , endTime2 -  endTime1 );

    }

}
