package com.example.redission.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() throws Exception{
        Config config = Config.fromYAML(redissonProperties.getConfig());
        Redisson.create(config);
        return Redisson.create(config);
    }

}
