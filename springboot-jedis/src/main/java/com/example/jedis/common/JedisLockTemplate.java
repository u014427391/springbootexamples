package com.example.jedis.common;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JedisLockTemplate extends AbstractRedisLock implements InitializingBean {

    private String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private String WATCH_DOG_LUA = "local lock_key=KEYS[1]\n" +
            "local lock_value=ARGV[1]\n" +
            "local lock_ttl=ARGV[2]\n" +
            "local current_value=redis.call('get',lock_key)\n" +
            "local result=0;\n" +
            "if lock_value==current_value then\n" +
            "    result=1;\n" +
            "    redis.call('expire',lock_key,lock_ttl)\n" +
            "end\n" +
            "return result";

    private static final Long UNLOCK_SUCCESS = 1L;

    private static final Long RETRY_INTERVAL = 1000L;
    private static final Integer DEFAULT_EXPIRE = 30;
    private static final Integer DEFAULT_TIMEOUT = 1000;


    @Autowired
    private JedisTemplate jedisTemplate;


    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);


    @Override
    public void afterPropertiesSet() throws Exception {
        this.UNLOCK_LUA = jedisTemplate.scriptLoad(UNLOCK_LUA);
        this.WATCH_DOG_LUA = jedisTemplate.scriptLoad(WATCH_DOG_LUA);
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
    public boolean release(String lockKey, String requestId) {
        Object eval = jedisTemplate.evalsha(UNLOCK_LUA, CollUtil.newArrayList(lockKey), CollUtil.newArrayList(requestId));
        if (UNLOCK_SUCCESS.equals(eval)) {
            scheduledThreadPoolExecutor.shutdown();
            return true;
        }
        return false;
    }

    private void watch(String lockKey, String lockValue, long expire) {
        if (scheduledThreadPoolExecutor.isShutdown()) {
            scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        }
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new WatchDogTask(scheduledThreadPoolExecutor, CollUtil.newArrayList(lockKey), CollUtil.newArrayList(lockValue)),
                1,
                expire,
                TimeUnit.SECONDS
        );
    }


    class WatchDogTask implements Runnable {
        private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
        private List<String> keys;
        private List<String> args;

        public WatchDogTask(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, List<String> keys, List<String> args) {
            this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
            this.keys = keys;
            this.args = args;
        }

        @Override
        public void run() {
            log.info("watch dog for renewal...");
            if (jedisTemplate.evalsha(WATCH_DOG_LUA, keys, args).equals(0)) {
                scheduledThreadPoolExecutor.shutdown();
            }
        }
    }

}
