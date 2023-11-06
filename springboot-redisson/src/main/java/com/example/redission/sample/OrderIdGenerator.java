package com.example.redission.sample;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderIdGenerator {

    @Resource
    private RedisTemplate redisTemplate;

    public Long getOrderId() {
        return redisTemplate.opsForValue().increment("orderId");
    }


}
