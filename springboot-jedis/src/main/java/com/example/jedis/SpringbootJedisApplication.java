package com.example.jedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@SpringBootApplication
@EnableAsync
public class SpringbootJedisApplication {

    @Resource
    RedisConnectionFactory factory;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootJedisApplication.class, args);
    }

    @PreDestroy
    public void flushDB() {
        factory.getConnection().flushDb();
    }

}
