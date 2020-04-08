package com.example.springboot.rabbitmq;

import com.example.springboot.rabbitmq.component.direct.DirectReceiver;
import com.example.springboot.rabbitmq.component.direct.DirectSender;
import com.example.springboot.rabbitmq.component.fanout.FanoutSender;
import com.example.springboot.rabbitmq.component.topic.TopicSender;
import com.example.springboot.rabbitmq.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {

    @Autowired
    private DirectSender directSender;
    @Autowired
    private DirectReceiver directReceiver;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    void contextLoads() {
    }

    @Test
    void directSend(){
        //directSender.send(1);
        User user = new User("nicky","123456");
        directSender.send(user);
    }

    @Test
    void fanoutSend(){
        fanoutSender.send();
    }

    @Test
    void topicSend(){
        //topicSender.send1();
        topicSender.send2();
    }

    @Test
    void helloReceive(){
        Object o = rabbitTemplate.receiveAndConvert("fanoutQueueA");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void oneToMany() throws Exception {
        for (int i=0;i<100;i++){
            directSender.send(i);
            Thread.sleep(300);
        }
    }




}
