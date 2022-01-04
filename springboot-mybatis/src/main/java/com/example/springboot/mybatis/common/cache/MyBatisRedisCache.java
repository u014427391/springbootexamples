package com.example.springboot.mybatis.common.cache;


import com.example.springboot.mybatis.common.ioc.SpringContextHolder;
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
            redisTemplate.opsForHash().put(id, key.toString() , value);
            log.info("mybatis缓存,{}:[{}]" , key , value );
        }
    }

    @Override
    public Object getObject(Object key) {
        if (!StringUtils.isEmpty(key)) {
            Object object = redisTemplate.opsForHash().get(id , key.toString());
            log.info("mybatis缓存读取,{}:[{}]", key , object);
            return object;
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
        redisTemplate.delete(id.toString());
    }

    @Override
    public int getSize() {
        return redisTemplate.opsForHash().size(id.toString()).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }


}
