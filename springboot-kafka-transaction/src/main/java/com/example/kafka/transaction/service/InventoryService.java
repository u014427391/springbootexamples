package com.example.kafka.transaction.service;

import cn.hutool.json.JSONUtil;
import com.example.kafka.transaction.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @KafkaListener(topics = "order-topic", groupId = "inventory-service")
    @Transactional("kafkaTransactionManager")
    public void handleOrder(ConsumerRecord<String, String> record) {
        log.info("监听订单创建:{}", record.value());
        Order order = JSONUtil.toBean(record.value(), Order.class);

        // 1. 扣减库存（本地事务）
        //inventoryRepository.deduct(order.getProductId(), order.getQuantity());

        // 2. 发送库存处理结果
        kafkaTemplate.send("inventory-result-topic", order.getId(), "SUCCESS");

        // 3. 手动提交偏移量（由事务管理器自动完成）
    }

}
