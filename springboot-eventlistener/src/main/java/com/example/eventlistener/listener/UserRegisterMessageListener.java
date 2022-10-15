package com.example.eventlistener.listener;

import com.example.eventlistener.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegisterMessageListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType){
        return eventType == UserRegisterEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType){
        return true;
    }

   
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("监听到用户注册，准备给新用户发送站内信...");
        log.info("user info:{}" , event.toString());
    }

    @Override
    public int getOrder(){
        return -1;
    }


}
