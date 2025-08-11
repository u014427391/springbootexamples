package com.example.kafka.transaction.service;

import cn.hutool.json.JSONUtil;
import com.example.kafka.transaction.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Transactional("kafkaTransactionManager") // 指定事务管理器
    public void createOrder(Order order) {
        // 1. 本地数据库事务：保存订单
        //orderRepository.save(order);

        // 2. 发送消息到 Kafka
        kafkaTemplate.send("order-topic", order.getId(), JSONUtil.toJsonStr(order));

        // 3. 模拟异常，验证事务回滚
        if ("error".equals(order.getId())) {
            throw new RuntimeException("模拟异常，测试事务回滚");
        }
    }


}
