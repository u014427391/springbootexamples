package com.example.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootRabbitmqDelayQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqDelayQueueApplication.class, args);
    }

}
