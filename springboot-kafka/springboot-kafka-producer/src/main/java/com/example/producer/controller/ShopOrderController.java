package com.example.producer.controller;

import com.example.ebus.event.ShopOrderEvent;
import com.example.ebus.publisher.MyEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ShopOrderController {

    @Autowired
    private MyEventPublisher eventPublisher;

    @PostMapping("/order")
    public String placeOrder(@RequestBody ShopOrderEvent orderEvent) {
        eventPublisher.publishEvent(orderEvent);
        return orderEvent.getOrderCode();
    }
}
