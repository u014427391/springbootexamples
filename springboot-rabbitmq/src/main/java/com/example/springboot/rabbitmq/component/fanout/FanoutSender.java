package com.example.springboot.rabbitmq.component.fanout;

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
 *    修改后版本:     修改人：  修改日期: 2020/04/08 11:00  修改内容:
 * </pre>
 */
@Component
public class FanoutSender {

    Logger LOG = LoggerFactory.getLogger(DirectSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String content = "hello!"+date;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOG.info("class:{},message:{}","FanoutSender",content);
        this.rabbitTemplate.convertAndSend("amq.fanout","",content,correlationData);
    }

}
