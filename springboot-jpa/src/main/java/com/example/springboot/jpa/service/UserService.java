package com.example.springboot.jpa.service;


import com.example.springboot.jpa.dto.UserSearchDto;
import com.example.springboot.jpa.entity.User;

import java.util.List;

public interface UserService {

    User getUser(Integer userId);

    User insertUser(User user);

    List<User> listUser(UserSearchDto searchDto);

}
