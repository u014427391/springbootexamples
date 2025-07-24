package com.example.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringbootRedisRateLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisRateLimitApplication.class, args);
    }

}
