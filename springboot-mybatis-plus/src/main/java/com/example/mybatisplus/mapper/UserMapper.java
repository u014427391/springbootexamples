package com.example.mybatisplus.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.User;

@DS(value = "shopping")
public interface UserMapper extends BaseMapper<User>{

}
