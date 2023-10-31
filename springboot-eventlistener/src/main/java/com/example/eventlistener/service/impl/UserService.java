package com.example.eventlistener.service.impl;

import com.example.eventlistener.event.UserRegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void register() {
        UserRegisterEvent userDto = new UserRegisterEvent(this);
        userDto.setUserId(10001L);
        userDto.setName("管理员");
        applicationEventPublisher.publishEvent(userDto);
    }

}
