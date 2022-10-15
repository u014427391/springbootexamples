package com.example.eventlistener.listener;

import com.example.eventlistener.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegisterSmsListener {

    @EventListener
    public void eventListener(UserRegisterEvent event) {
        log.info("监听到用户注册，准备发送短信...");
        log.info("user info:{}" , event.toString());
    }

}
