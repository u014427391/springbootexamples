package com.example.eventlistener.controller;

import com.example.eventlistener.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api")
public class SampleController {

    @Resource
    private UserServiceImpl userService;

    @GetMapping(value = "/register")
    public void register() {
        userService.register();
    }

    @GetMapping(value = "/sendMsgAfterRegisterByEvent")
    public void registerAfterSendMsgByEvent() {
        userService.sendMsgAfterRegisterByEvent();
    }

    @GetMapping(value = "/sendMsgAfterRegister")
    public void sendMsgAfterRegister() {
        userService.sendMsgAfterRegister();
    }

}
