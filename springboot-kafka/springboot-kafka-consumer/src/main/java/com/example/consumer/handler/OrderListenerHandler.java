package com.example.consumer.handler;


import com.example.ebus.event.ShopOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListenerHandler {

    @KafkaListener(topics = {"${app.ebus.topic:ebus}"})
    public void obtainTopicData(ShopOrderEvent event) {
        log.info("下单成功,orderCode:{}" , event.getOrderCode());
    }

}
