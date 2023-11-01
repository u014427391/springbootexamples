package com.example.eventlistener.listener;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.example.eventlistener.event.SendMsgEvent;
import com.example.eventlistener.mapper.UserMapper;
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
//    @EventListener
    public void sendMsg(SendMsgEvent sendMsgEvent) {
        log.info("sendMsg: {}" , JSONUtil.toJsonStr(sendMsgEvent));

        String result = HttpRequest
                .get("http://127.0.0.1:8081/api/user/"+sendMsgEvent.getUserId())
                .execute().body();
        log.info("result:{}" , result);
    }


}
