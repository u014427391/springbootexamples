package com.example.delayqueue.controller;

import cn.hutool.core.util.IdUtil;
import com.example.delayqueue.core.Message;
import com.example.delayqueue.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("test")
    public void test() {
        Message message = Message.builder()
                .id(IdUtil.fastSimpleUUID())
                .value("msg"+IdUtil.fastSimpleUUID())
                .topic("testTopic")
                .delayTime(3)
                .build();
        messageProducer.pushMessage(message);
    }

}
