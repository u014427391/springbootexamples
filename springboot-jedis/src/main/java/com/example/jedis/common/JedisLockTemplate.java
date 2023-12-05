package com.example.jedis.common;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class JedisLockTemplate {

    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private static final Long UNLOCK_SUCCESS = 1L;

    private static final Long RETRY_INTERVAL = 100L;
    private static final Integer DEFAULT_EXPIRE = 30;
    private static final Long DEFAULT_TIMEOUT = 300L;

    @Resource
    private JedisTemplate jedisTemplate;


    public Boolean lock(String lockKey, String requestId, int expire, long timeout) {
        expire = expire <= 0 ? DEFAULT_EXPIRE : expire;
        timeout = timeout < 0 ? DEFAULT_TIMEOUT : timeout;

        Boolean canLock = false;
        long start = System.currentTimeMillis();
        try {
            do {
                canLock = jedisTemplate.setnxex(lockKey, requestId, expire);
                TimeUnit.MILLISECONDS.sleep(RETRY_INTERVAL);
            } while (System.currentTimeMillis() - start < timeout);
        } catch (InterruptedException e) {
        }
        return canLock;
    }

    public Boolean unlock(String lockKey, String requestId) {
        Object eval = jedisTemplate.eval(UNLOCK_LUA, CollUtil.newArrayList(lockKey), CollUtil.newArrayList(requestId));
        if (UNLOCK_SUCCESS.equals(eval)) {
            return true;
        }
        return false;
    }

}
