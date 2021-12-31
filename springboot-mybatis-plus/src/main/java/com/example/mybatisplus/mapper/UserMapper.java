package com.example.mybatisplus.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.common.cache.MyBatisRedisCache;
import com.example.mybatisplus.model.User;
import org.apache.ibatis.annotations.CacheNamespace;

@DS(value = "shop")
@CacheNamespace(implementation = MyBatisRedisCache.class)
public interface UserMapper extends BaseMapper<User>{

}
