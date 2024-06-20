package com.example.springboot.websocket.rest;

import cn.hutool.json.JSONUtil;
import com.example.springboot.websocket.dto.WebSocketDto;
import com.example.springboot.websocket.message.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Slf4j
public class WebSocketApiController {

    @Resource
    @Qualifier("webSocket")
    private WebSocket webSocket;

    @PostMapping
    @RequestMapping("/sendMessage")
    public ResultBean<Boolean> sendMessage(@RequestBody WebSocketDto sendDto) {
        log.info("webSocket发送消息给客户端:{}", JSONUtil.toJsonStr(sendDto));
        try {
            webSocket.sendMessageToClient(sendDto.getMessage(), sendDto.getSocketClient());
            return ResultBean.ok(true);
        } catch (Exception e) {
            log.error("发送WebSocket消息异常:{}", e);
            return ResultBean.badRequest("发送WebSocket消息异常", false);
        }
    }

}
