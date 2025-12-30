package com.example.pulsar.controller;

import com.example.pulsar.model.Message;
import com.example.pulsar.service.PulsarProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pulsar")
public class PulsarController {

    private final PulsarProducerService producerService;

    public PulsarController(PulsarProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        producerService.send(message);
        return "Message sent to Pulsar successfully!";
    }
}
