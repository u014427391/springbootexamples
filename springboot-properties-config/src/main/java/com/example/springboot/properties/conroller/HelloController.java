package com.example.springboot.properties.conroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = {"/hello","/sayHello"})
    public String sayHello(){
        return "hello world!";
    }
}
