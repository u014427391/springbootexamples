package com.example.springboot.rabbitmq.component.topic;

import com.example.springboot.rabbitmq.component.direct.DirectReceiver;
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
 *    修改后版本:     修改人：  修改日期: 2020/04/08 11:09  修改内容:
 * </pre>
 */
@Component
@RabbitListener(queues = {"topicQueueA"})
public class TopicReceiverA {

    Logger LOG = LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitHandler
    public void receiverMsg(String msg){
        LOG.info("class:{},message:{}","TopicReceiverA",msg);
    }
}
