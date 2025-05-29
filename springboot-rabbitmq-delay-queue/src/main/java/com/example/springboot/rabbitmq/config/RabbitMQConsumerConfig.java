package com.example.springboot.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConsumerConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory manualAckContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // 配置手动确认模式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        // 配置并发消费者
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(10);

        // 配置重试策略
        RetryTemplate retryTemplate = new RetryTemplate();

        // 设置指数退避策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(5000); // 初始间隔5秒
        backOffPolicy.setMultiplier(2.0);      // 间隔翻倍
        backOffPolicy.setMaxInterval(20000);  // 最大间隔20秒
        retryTemplate.setBackOffPolicy(backOffPolicy);

        // 设置最大重试次数
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3); // 最多重试3次
        retryTemplate.setRetryPolicy(retryPolicy);

        factory.setRetryTemplate(retryTemplate);

        // 设置消息转换器
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        // 设置错误处理策略
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
                new CustomFatalExceptionStrategy()
        ));

        return factory;
    }

    // 自定义致命异常策略，控制哪些异常应该重试，哪些应该直接拒绝
    private static class CustomFatalExceptionStrategy implements FatalExceptionStrategy {
        @Override
        public boolean isFatal(Throwable t) {
            // 示例：业务异常直接拒绝，系统异常重试
            return t instanceof IllegalArgumentException;
        }
    }
}