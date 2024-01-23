package com.example.springboot.jpa.controller;

import com.example.springboot.jpa.dto.UserSearchDto;
import com.example.springboot.jpa.entity.User;
import com.example.springboot.jpa.repository.UserRepository;
import com.example.springboot.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") Integer userId){
        User user = userService.getUser(userId);
        return user;
    }

    @GetMapping("/user")
    public User insertUser(User user){
        User save = userService.insertUser(user);
        return save;
    }

    @PostMapping("/user/list")
    public List<User> listUser(@RequestBody UserSearchDto searchDto) {
        return userService.listUser(searchDto);
    }

}