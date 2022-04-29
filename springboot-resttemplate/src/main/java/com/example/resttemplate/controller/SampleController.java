package com.example.resttemplate.controller;

import com.example.resttemplate.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SampleController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/users/{login}")
    public UserDto getUser(@PathVariable("login")String login) {
        String url = new StringBuilder()
                .append("https://api.github.com/users/")
                .append(login)
                .toString();
        UserDto userDto = restTemplate.getForObject(url , UserDto.class , "");
        return userDto;
    }



}
