package com.example.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@SpringBootApplication
public class SpringbootJedisApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootJedisApplication.class, args);
    }



}
