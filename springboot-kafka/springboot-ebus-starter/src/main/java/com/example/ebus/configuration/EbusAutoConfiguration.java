package com.example.ebus.configuration;

import com.example.ebus.publisher.MyEventPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EbusAutoConfiguration {

    @Bean
    public MyEventPublisher myEventPublisher(@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
        return new MyEventPublisher(kafkaTemplate);
    }

}
