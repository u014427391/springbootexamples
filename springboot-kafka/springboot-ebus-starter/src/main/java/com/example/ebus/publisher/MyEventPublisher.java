package com.example.ebus.publisher;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class MyEventPublisher {

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.ebus.topic:abus}")
    private String topic;

    public MyEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(ApplicationEvent event) {
        if (log.isInfoEnabled()) {
            log.info("topic发送:{}", event.getClass().getName());
        }
        kafkaTemplate.send(topic, event);
    }

}
