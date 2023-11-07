package com.example.redis.common.handlers;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <pre>
 *      Redis分布式ID生成器
 * </pre>
 *
 * <pre>
 * @author nicky
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2023/11/07 14:18  修改内容:
 * </pre>
 */
@Component
public class RedisIdentifierGenerator implements IdentifierGenerator {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Number nextId(Object entity) {
        String key = entity.getClass().getName();
        return redisTemplate.opsForValue().increment(key);
    }

}
