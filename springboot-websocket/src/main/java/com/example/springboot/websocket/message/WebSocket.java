package com.example.springboot.websocket.message;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


@ServerEndpoint("/ws/webSocketServer")
@Component
@Slf4j
public class WebSocket {

    private static final String PREFIX = "socketClient=";

    private String socketClientCode;

    // 线程安全的CopyOnWriteArrayList
    private static CopyOnWriteArrayList<WebSocket> webSocketSet = new CopyOnWriteArrayList<>();

    private Session session ;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("open a webSocket {}, online num: {}",getSocketClientCode(), getOnlineNum());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.error("close a webSocket {}, online num:{}", getSocketClientCode(), getOnlineNum());
        printOnlineClientCode();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        webSocketSet.remove(this);
        log.error("webSocket error {}, {}, online num:{}", error, getSocketClientCode(), getOnlineNum());
        printOnlineClientCode();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive message from client:{}", message);
        // 业务实现
        if (message.startsWith(PREFIX)) {
            String socketClientCode = message.substring(PREFIX.length());
            this.setSocketClientCode(socketClientCode);
            sendMessage(message);
            printOnlineClientCode();
        } else {
            sendMessage(message);
        }
    }

    /**
     * 发送消息
     *
     * @Date 2024/06/19 16:36
     * @Param [message]
     * @return void
     */
    public void sendMessage(String message) {
        if (!this.session.isOpen()) {
            log.warn("webSocket is close");
            return;
        }
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("sendMessage exception:{}", e);
        }
    }

    /**
     * 给客户端发送消息
     *
     * @Date 2024/06/19 16:37
     * @Param [message, socketClientCode]
     * @return void
     */
    public void sendMessageToClient(String message, String socketClientCode) {
        log.info("send message to client, message:{}, clientCode:{}", message, socketClientCode);

        printOnlineClientCode();

        webSocketSet.stream().forEach(ws -> {
            if (StrUtil.isNotBlank(socketClientCode) && StrUtil.isNotBlank(ws.getSocketClientCode()) && ws.getSocketClientCode().equals(socketClientCode)) {
                ws.sendMessage(message);
            }
        });
    }

    /**
     * 群发消息
     *
     * @Date 2024/06/19 16:37
     * @Param [message]
     * @return void
     */
    public void fanoutMessage(String message) {
        webSocketSet.forEach(ws -> {
            ws.sendMessage(message);
        });
    }

    private static synchronized int getOnlineNum() {
        return webSocketSet.size();
    }

    private void printOnlineClientCode() {
        webSocketSet.stream().forEach(ws -> {
            log.info("webSocket online:{}", ws.getSocketClientCode());
        });
    }

    public String getSocketClientCode() {
        return socketClientCode;
    }

    public void setSocketClientCode(String socketClientCode) {
        this.socketClientCode = socketClientCode;
    }
}
