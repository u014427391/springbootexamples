package com.example.springboot.rabbitmq.component.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/08 10:54  修改内容:
 * </pre>
 */
@Component
@RabbitListener(queues = {"fanoutQueueB"})
public class FanoutReceiverB {

    Logger LOG = LoggerFactory.getLogger(FanoutReceiverB.class);

    @RabbitHandler
    public void process(String hello) {
        LOG.info("BReceiver  : " + hello + "/n");
    }
}
