package com.example.resttemplate.controller;

import com.example.resttemplate.configuration.RestGetUriTemplateHandler;
import com.example.resttemplate.configuration.RestTemplateWrapper;
import com.example.resttemplate.model.UserDto;
import com.example.resttemplate.model.request.QueryDto;
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


    @GetMapping(value = {"/users/get"})
    public UserDto get(QueryDto queryDto) {
//        UserDto userDto = restTemplate.getForObject("https://api.github.com/users/mojombo" , UserDto.class , queryDto);
        restTemplate.setUriTemplateHandler(new RestGetUriTemplateHandler());
        RestTemplateWrapper restTemplateWrapper = new RestTemplateWrapper(restTemplate);
        UserDto userDto = restTemplateWrapper.getForObject("https://api.github.com/users/mojombo" , UserDto.class, queryDto);
        return userDto;
    }


}
