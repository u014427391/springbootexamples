package com.example.eventlistener.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eventlistener.model.User;

@DS(value = "testDB")
public interface UserMapper extends BaseMapper<User>{

}
