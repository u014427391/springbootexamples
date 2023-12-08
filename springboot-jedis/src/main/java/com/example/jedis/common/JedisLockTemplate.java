package com.example.jedis.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JedisLockTemplate extends AbstractRedisLock implements InitializingBean {

    private String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private String WATCH_DOG_LUA = "local lock_key=KEYS[1]\n" +
            "local lock_value=ARGV[1]\n" +
            "local lock_ttl=ARGV[2]\n" +
            "local current_value=redis.call('get',lock_key)\n" +
            "local result=0\n" +
            "if lock_value==current_value then\n" +
            "    redis.call('expire',lock_key,lock_ttl)\n" +
            "    result=1\n" +
            "end\n" +
            "return result";

    private static final Long UNLOCK_SUCCESS = 1L;

    @Autowired
    private JedisTemplate jedisTemplate;

    private HashedWheelTimer hashedWheelTimer;

    @Autowired
    private ThreadPoolTaskExecutor executor;



    @Override
    public void afterPropertiesSet() throws Exception {
        this.UNLOCK_LUA = jedisTemplate.scriptLoad(UNLOCK_LUA);
        this.WATCH_DOG_LUA = jedisTemplate.scriptLoad(WATCH_DOG_LUA);
        hashedWheelTimer = new HashedWheelTimer(
                new DefaultThreadFactory("watchdog-timer"),
                100,
                TimeUnit.MILLISECONDS,
                1024
        );
    }


    @Override
    public boolean doAcquire(String lockKey, String requestId, int expire) {
        Boolean canLock = false;
        canLock = jedisTemplate.setnxex(lockKey, requestId, expire);
        if (canLock) {
            watch(lockKey, requestId, expire);
            return canLock;
        }
        return canLock;
    }

    @Override
    public boolean doRelease(String lockKey, String requestId) {
        Object eval = jedisTemplate.evalsha(UNLOCK_LUA, CollUtil.newArrayList(lockKey), CollUtil.newArrayList(requestId));
        if (UNLOCK_SUCCESS.equals(eval)) {
            hashedWheelTimer.stop();
            return true;
        }
        return false;
    }

    private void watch(String lockKey, String requestId, long expire) {
        Queue<Timeout> delayTaskQueue = getDelayTaskQueue(lockKey, requestId, expire);
        while(!delayTaskQueue.isEmpty()) {
            delayTaskQueue.poll();
        }
    }

    private Queue<Timeout> getDelayTaskQueue(String lockKey, String requestId, long expire) {
        Queue<Timeout> delayTaskQueue = new LinkedList<>();
        delayTaskQueue.offer(hashedWheelTimer.newTimeout(t -> {
                log.info("watch dog for renewal...");
                jedisTemplate.evalsha(WATCH_DOG_LUA,
                        CollUtil.newArrayList(lockKey),
                        CollUtil.newArrayList(requestId, Convert.toStr(expire)));
                log.info("renewal success, lockKey:{}, requestId:{}, expire::{}", lockKey, requestId, expire);
            },
            expire,
            TimeUnit.SECONDS));

        return delayTaskQueue;
    }



}
