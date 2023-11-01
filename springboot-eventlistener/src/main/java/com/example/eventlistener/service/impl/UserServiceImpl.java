package com.example.eventlistener.service.impl;

import cn.hutool.http.HttpRequest;
import com.example.eventlistener.event.SendMsgEvent;
import com.example.eventlistener.event.UserRegisterEvent;
import com.example.eventlistener.mapper.UserMapper;
import com.example.eventlistener.model.User;
import com.example.eventlistener.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements ApplicationEventPublisherAware , IUserService {

    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private UserMapper userMapper;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void register() {
        UserRegisterEvent userDto = new UserRegisterEvent(this);
        userDto.setUserId(10001L);
        userDto.setName("管理员");
        applicationEventPublisher.publishEvent(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMsgAfterRegisterByEvent() {
        User user = doRegister();

        // after transaction commit
        SendMsgEvent sendMsgEvent = new SendMsgEvent(this);
        sendMsgEvent.setUserId(user.getId());
        sendMsgEvent.setUserName(user.getName());
        applicationEventPublisher.publishEvent(sendMsgEvent);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMsgAfterRegister() {
        // register
        User user = doRegister();

        // after transaction commit
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                String result = HttpRequest
                        .get("http://127.0.0.1:8081/api/user/"+user.getId())
                        .execute().body();
                log.info("result:{}" , result);
            }
        });

    }


    private User doRegister() {
        User user = User.builder()
                .name("管理员")
                .email("123456@qq.com")
                .build();
        userMapper.insert(user);
        log.info("save user info");
        return user;
    }


}
