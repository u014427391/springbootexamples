package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.model.UserDO;
import com.example.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper , UserDO> implements IUserService {

}
