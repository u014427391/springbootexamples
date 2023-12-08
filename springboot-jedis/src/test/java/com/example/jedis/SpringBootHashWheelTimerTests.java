package com.example.jedis;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class SpringBootHashWheelTimerTests {



    @Test
    void testHashedWheelTimer() {
        simpleHashedWheelTimer();

        reScheduleHashedWheelTimer();
    }

    @SneakyThrows
    public static void simpleHashedWheelTimer() {
        log.info("init task 1...");

        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);

        // add a new timeout
        timer.newTimeout(timeout -> {
            log.info("running task 1...");
        }, 1, TimeUnit.SECONDS);
    }

    @SneakyThrows
    public static void reScheduleHashedWheelTimer() {
        log.info("init task 2...");

        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);

        Thread.sleep(3000);

        // add a new timeout
        Timeout tm = timer.newTimeout(timeout -> {
            log.info("running task 2...");
        }, 1, TimeUnit.SECONDS);

        // cancel
        if (!tm.isExpired()) {
            log.info("cancel task 2...");
            tm.cancel();
        }

        // reschedule
        timer.newTimeout(tm.task(), 1, TimeUnit.SECONDS);
    }

}
