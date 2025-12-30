package com.example.pulsar.model;

import lombok.Data;

@Data
public class Message {
    private String content;
    private String sender;
}
