package com.example.springboot.websocket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebSocketDto implements Serializable {

    private static final long serialVersionUID = 1010358239992637012L;
    private String socketClient;

    private String message;

}
