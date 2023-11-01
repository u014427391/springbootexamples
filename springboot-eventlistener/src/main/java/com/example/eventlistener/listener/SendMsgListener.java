package com.example.eventlistener.listener;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.eventlistener.event.SendMsgEvent;
import com.example.eventlistener.mapper.UserMapper;
import com.example.eventlistener.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

@Component
@Slf4j
public class SendMsgListener {

    @Resource
    private UserMapper userMapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT , classes = SendMsgEvent.class)
    public void sendMsg(SendMsgEvent sendMsgEvent) {
        log.info("sendMsg: {}" , JSONUtil.toJsonStr(sendMsgEvent));
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getId , sendMsgEvent.getUserId());
        User user = userMapper.selectOne(wrapper);
        log.info("user:{}" , JSONUtil.toJsonStr(user));

        LambdaUpdateWrapper<User> updateWp = Wrappers.lambdaUpdate();
        updateWp.eq(User::getId , user.getId());
        user.setName("系统管理员");
        userMapper.update(user, updateWp);
        log.info("update user");
    }


}
