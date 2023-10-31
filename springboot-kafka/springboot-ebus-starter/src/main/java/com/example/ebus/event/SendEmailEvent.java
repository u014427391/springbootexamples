package com.example.ebus.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailEvent {

    private String title;
    private String content;
    private String receiveUser;
    private String sendUser;


}
