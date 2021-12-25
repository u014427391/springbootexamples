package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.User;
import org.apache.ibatis.annotations.Mapper;

//@DS(value = "shop")
@Mapper
public interface UserMapper extends BaseMapper<User>{

}
