package com.example.ali.canal.client;


import com.example.ebus.publisher.MyEventPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class AliCanalClient implements InitializingBean {

    @Autowired
    private MyEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
