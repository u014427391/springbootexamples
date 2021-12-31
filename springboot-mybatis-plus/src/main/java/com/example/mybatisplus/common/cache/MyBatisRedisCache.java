package com.example.mybatisplus.common.cache;


import com.example.mybatisplus.common.ioc.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class MyBatisRedisCache implements Cache {

    private static RedisTemplate<String , Object> redisTemplate;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private String id;

    public MyBatisRedisCache(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("cache instances require an id.");
        }
        this.id = id;
        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean("redisTemplate");
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (!StringUtils.isEmpty(value)) {
            redisTemplate.opsForValue().set( key.toString() , value);
            log.info("mybatis缓存,{}:{}" , key , value);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (!StringUtils.isEmpty(key)) {
            log.info("mybatis缓存读取：{}", key);
            return redisTemplate.opsForValue().get( key);

        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (!StringUtils.isEmpty(key)) {
            redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return redisTemplate.opsForHash().size(id).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }


}
