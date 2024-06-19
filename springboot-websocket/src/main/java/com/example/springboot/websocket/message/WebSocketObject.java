package com.example.springboot.websocket.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


@ServerEndpoint("/ws/webSocketServer")
@Component
@Slf4j
public class WebSocketObject {

    private static CopyOnWriteArrayList<WebSocketObject> webSocketSet = new CopyOnWriteArrayList<>();

    private Session session ;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("open a webSocket ,size: {}", getSize());
    }

    @OnClose
    public void onClose() {
        log.error("close a webSocket");
        webSocketSet.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("webSocket error：{}", error);
        webSocketSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive message from client:{}", message);
    }

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

    public void fanoutMessage(String message) {
        webSocketSet.forEach(ws -> {
            ws.sendMessage(message);
        });
    }

    private static synchronized int getSize() {
        return webSocketSet.size();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
