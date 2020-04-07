package com.example.springboot.rabbitmq.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *   消息消费者
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/07 13:47  修改内容:
 * </pre>
 */
@Component
@RabbitListener(queues = {"directQueue"})
public class HelloReceiver {
    Logger LOG = LoggerFactory.getLogger(HelloReceiver.class);

    @RabbitHandler
    public void receiverMsg(String msg){
        LOG.info("class:{},message:{}","HelloReceiver",msg);
    }
}
