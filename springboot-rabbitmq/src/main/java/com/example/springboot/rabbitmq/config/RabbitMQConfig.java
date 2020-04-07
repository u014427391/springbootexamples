package com.example.springboot.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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

    @Bean
    public Queue simpleQueue(){
        return new Queue("simpleQueue") ;
    }


}
