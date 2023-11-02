package com.example.mybatisplus.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.common.cache.MyBatisRedisCache;
import com.example.mybatisplus.model.UserDO;
import org.apache.ibatis.annotations.CacheNamespace;

@DS(value = "shop")
@CacheNamespace(implementation = MyBatisRedisCache.class ,eviction = MyBatisRedisCache.class)
public interface UserMapper extends BaseMapper<UserDO>{

}
