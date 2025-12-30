package com.example.pulsar.service;

import com.example.pulsar.model.Message;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.pulsar.listener.Acknowledgement;
import org.springframework.stereotype.Service;

@Service
public class PulsarConsumerService {

    @PulsarListener(
            subscriptionName = "my-subscription",
            topics = "demo-topic",
            subscriptionType = SubscriptionType.Shared,
            ackMode = AckMode.MANUAL // 手动模式
    )
    public void consume(Message message, Acknowledgement ack) {
        try {
            System.out.println("Received message: " + message.getContent());
            ack.acknowledge();
        } catch (Exception e) {
            ack.nack();
        }
    }
}
