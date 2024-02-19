package com.example.delayqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootRedisDelayQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisDelayQueueApplication.class, args);
    }

}
