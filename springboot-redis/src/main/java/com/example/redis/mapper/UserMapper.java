package com.example.redis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.redis.model.UserDO;

@DS(value = "shop")
public interface UserMapper extends BaseMapper<UserDO> {

}
