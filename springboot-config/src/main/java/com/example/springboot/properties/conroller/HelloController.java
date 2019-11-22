package com.example.springboot.properties.conroller;

import com.example.springboot.properties.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    User user;

    @GetMapping(value = {"/hello","/sayHello"})
    public String sayHello(){
        //return "hello world!";
        return  user.toString();
    }
}
