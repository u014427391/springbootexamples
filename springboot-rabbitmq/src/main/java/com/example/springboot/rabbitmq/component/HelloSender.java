package com.example.springboot.rabbitmq.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 *   消息生产者
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/07 13:42  修改内容:
 * </pre>
 */
@Component
public class HelloSender {

    Logger LOG = LoggerFactory.getLogger(HelloSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(int i) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String content = i+":hello!"+date;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOG.info("class:{},message:{}","HelloSender",content);
        this.rabbitTemplate.convertAndSend("exchanges.direct","direct_routingKey",content,correlationData);
    }

}
