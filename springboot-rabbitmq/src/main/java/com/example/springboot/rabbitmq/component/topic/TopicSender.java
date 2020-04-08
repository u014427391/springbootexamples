package com.example.springboot.rabbitmq.component.topic;

import com.example.springboot.rabbitmq.component.direct.DirectSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/08 11:10  修改内容:
 * </pre>
 */
@Component
public class TopicSender {

    Logger LOG = LoggerFactory.getLogger(DirectSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String content = "hello!"+date;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOG.info("class:{},message:{}","TopicSender",content);
        this.rabbitTemplate.convertAndSend("amq.topic","topic.msg",content,correlationData);
    }

    public void send2() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String content = "hello!"+date;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOG.info("class:{},message:{}","TopicSender",content);
        this.rabbitTemplate.convertAndSend("amq.topic","topic.msg1",content,correlationData);
    }
}
