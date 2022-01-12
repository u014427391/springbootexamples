package com.example.validated.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.validated.model.User;
import org.apache.ibatis.annotations.Mapper;

@DS(value = "shop")
@Mapper
public interface UserMapper extends BaseMapper<User>{

}
