package com.example.springboot.rabbitmq;

import com.example.springboot.rabbitmq.component.HelloReceiver;
import com.example.springboot.rabbitmq.component.HelloSender;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private HelloReceiver helloReceiver;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    void contextLoads() {
    }

    @Test
    void helloSend(){
        helloSender.send(1);
    }

    @Test
    void helloReceive(){
        Object o = rabbitTemplate.receiveAndConvert("hello");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void oneToMany() throws Exception {
        for (int i=0;i<100;i++){
            helloSender.send(i);
            Thread.sleep(300);
        }
    }




}
