package com.example.delayqueue.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.delayqueue.core.Message;
import com.example.delayqueue.core.RedisDelayQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Autowired
    private RedisDelayQueue redisDelayQueue;

    public void pushMessage(Message message) {
        log.info("push message:{},now:{}", JSONUtil.toJsonStr(message), DateUtil.now());
        redisDelayQueue.push(message);
    }

}
