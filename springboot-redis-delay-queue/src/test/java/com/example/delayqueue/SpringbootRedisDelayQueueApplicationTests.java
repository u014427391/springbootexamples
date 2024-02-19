package com.example.delayqueue;

import cn.hutool.core.util.IdUtil;
import com.example.delayqueue.core.Message;
import com.example.delayqueue.service.MessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRedisDelayQueueApplicationTests {


    @Autowired
    private MessageProducer messageProducer;

    @Test
    void contextLoads() {
        Message message = Message.builder()
                .id(IdUtil.fastSimpleUUID())
                .value("msg"+IdUtil.fastSimpleUUID())
                .topic("testTopic")
                .delayTime(3)
                .build();
        messageProducer.pushMessage(message);

    }

}
