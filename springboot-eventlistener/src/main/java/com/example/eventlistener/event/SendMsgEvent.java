package com.example.eventlistener.event;

import org.springframework.context.ApplicationEvent;


public class SendMsgEvent extends ApplicationEvent {

    private Long userId;

    private String userName;

    public SendMsgEvent(Object source){
        super(source);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
