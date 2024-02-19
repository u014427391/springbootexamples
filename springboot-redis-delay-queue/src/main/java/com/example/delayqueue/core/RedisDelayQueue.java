package com.example.delayqueue.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisDelayQueue implements DelayQueue {

    private static final String DELAY_QUEUE_NAME = "delay_queue";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean push(Message message) {
        long score = System.currentTimeMillis() + message.getDelayTime() * 1000;
        String msg = JSONUtil.toJsonStr(message);
        return redisTemplate.opsForZSet().add(DELAY_QUEUE_NAME, msg, score);
    }

    @Override
    public boolean remove(Message message) {
        String msg = JSONUtil.toJsonStr(message);
        Long remove = redisTemplate.opsForZSet().remove(DELAY_QUEUE_NAME, msg);
        return remove > 0 ? true : false;
    }

    @Override
    public List<Message> pull() {
        List<Message> msgList = CollUtil.newArrayList();
        try {
            Set<String> stringSet = Optional.ofNullable(redisTemplate.opsForZSet().rangeByScore(DELAY_QUEUE_NAME, 0, System.currentTimeMillis())).orElse(CollUtil.newHashSet());
            msgList = stringSet.stream().map(str -> {
                Message message = null;
                try {
                    JSONUtil.toBean(str, Message.class);
                } catch (ConvertException e) {
                    log.error("toBean exception:{}", e);
                }
                return message;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("exception:{}", e);
        }

        return msgList;
    }
}
