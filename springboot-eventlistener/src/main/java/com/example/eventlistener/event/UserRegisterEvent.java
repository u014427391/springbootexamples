package com.example.eventlistener.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class UserRegisterEvent extends ApplicationEvent {

    private Long userId;

    private String name;

    public UserRegisterEvent(Object source){
        super(source);
    }

}
