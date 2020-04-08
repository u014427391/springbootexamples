package com.example.springboot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * <pre>
 *      RabbitMQ配置类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/07 11:48  修改内容:
 * </pre>
 */
@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    //@Primary
    public AmqpTemplate amqpTemplate(){
        Logger LOG = LoggerFactory.getLogger(AmqpTemplate.class);
        //使用jackson 消息转换器(发送对象时候才开启)
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        // 开启returncallback    yml 需要配置publisher-returns: true
        rabbitTemplate.setReturnCallback(((message,  replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            LOG.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        }));
        //开启消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) ->{
            if (ack) {
               LOG.info("消息发送到交换机成功,correlationId:{}",correlationData.getId());
            } else {
                LOG.info("消息发送到交换机失败,原因:{}",cause);
            }
        } ));
        return rabbitTemplate;
    }

    /*  ****************************************** direct exchange ********************************************** */

    /**
     * 声明直连交换机 支持持久化.
     * @return the exchange
     */
    @Bean("directExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("amq.direct").durable(true).build();
    }

    @Bean("directQueue")
    public Queue directQueue(){
        return new Queue("directQueue", true, true, true);
        //return QueueBuilder.durable("directQueue").build();
    }

    @Bean
    public Binding directBinding(@Qualifier("directQueue")Queue queue,@Qualifier("directExchange")Exchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("direct_routingKey").noargs();
    }

    /*  ****************************************** fanout exchange ********************************************** */

    @Bean("fanoutQueueA")
    public Queue fanoutQueueA(){
        return new Queue("fanoutQueueA", true, true, true);
    }

    @Bean("fanoutQueueB")
    public Queue fanoutQueueB(){
        return new Queue("fanoutQueueB", true, true, true);
    }

    @Bean("fanoutQueueC")
    public Queue fanoutQueueC(){
        return new Queue("fanoutQueueC", true, true, true);
    }

    /**
     * 声明一个Fanout类型的交换器
     * @Author mazq
     * @Date 2020/04/08 11:25
     * @Param []
     * @return org.springframework.amqp.core.FanoutExchange
     */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding fanoutABinding(@Qualifier("fanoutQueueA")Queue queue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBBinding(@Qualifier("fanoutQueueB")Queue queue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutCBinding(@Qualifier("fanoutQueueC")Queue queue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /*  ****************************************** topic exchange ********************************************** */

    @Bean("topicQueueA")
    public Queue topicQueueA(){
        return new Queue("topicQueueA",true, true, true);
    }

    @Bean("topicQueueB")
    public Queue topicQueueB(){
        return new Queue("topicQueueB",true, true, true);
    }

    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding topicABinding(@Qualifier("topicQueueA")Queue queue,TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.msg");
    }

    @Bean
    public Binding topicBBinding(@Qualifier("topicQueueB")Queue queue,TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.#");
    }



}
