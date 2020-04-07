package com.example.springboot.rabbitmq;

import com.example.springboot.rabbitmq.component.HelloReceiver;
import com.example.springboot.rabbitmq.component.HelloSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private HelloReceiver helloReceiver;

    @Test
    void contextLoads() {
    }

    @Test
    void helloSend(){
        helloSender.send();
    }

    @Test
    void helloReceive(String msg){
        helloReceiver.receiverMsg(msg);
    }

}
