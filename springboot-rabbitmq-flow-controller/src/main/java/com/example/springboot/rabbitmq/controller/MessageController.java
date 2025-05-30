package com.example.springboot.rabbitmq.controller;

import com.example.springboot.rabbitmq.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    // 发送单条消息
    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(message);
        return "消息已发送";
    }

    // 批量发送消息，用于测试流量控制
    @PostMapping("/send/batch/{count}")
    public String sendBatchMessages(@PathVariable int count) {
        for (int i = 0; i < count; i++) {
            messageProducer.sendMessage("测试消息-" + i);
            // 添加短暂延迟，避免过快发送导致的问题
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return "批量消息发送请求已提交，共" + count + "条消息";
    }
}