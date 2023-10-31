package com.example.validated.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.validated.mapper.UserMapper;
import com.example.validated.model.User;
import com.example.validated.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
