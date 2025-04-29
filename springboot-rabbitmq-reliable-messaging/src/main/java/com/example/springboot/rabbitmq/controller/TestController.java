package com.example.springboot.rabbitmq.controller;

import com.example.springboot.rabbitmq.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private MessageSender messageSender;

    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello, RabbitMQ!";
        messageSender.sendMessage(message);
        return "消息发送成功";
    }
}