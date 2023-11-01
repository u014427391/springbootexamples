package com.example.eventlistener.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
                LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(User::getId , user.getId());
                User user = userMapper.selectOne(wrapper);
                log.info("select user:{}" , JSONUtil.toJsonStr(user));

                LambdaUpdateWrapper<User> updateWp = Wrappers.lambdaUpdate();
                updateWp.eq(User::getId , user.getId());
                user.setName("系统管理员");
                userMapper.update(user, updateWp);
                log.info("update user");

//                updateUser(user);
            }
        });

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        LambdaUpdateWrapper<User> updateWp = Wrappers.lambdaUpdate();
        updateWp.eq(User::getId , user.getId());
        user.setName("系统管理员");
        userMapper.update(user, updateWp);
        log.info("update user");
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
