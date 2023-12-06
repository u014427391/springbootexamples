package com.example.jedis.common;

import java.util.concurrent.TimeUnit;

import static com.example.jedis.common.RedisConstant.DEFAULT_EXPIRE;
import static com.example.jedis.common.RedisConstant.DEFAULT_TIMEOUT;

public abstract class AbstractDistributedLock implements DistributedLock {

    @Override
    public boolean acquire(String lockKey, String requestId, int expireTime, int timeout) {
        expireTime = expireTime <= 0 ? DEFAULT_EXPIRE : expireTime;
        timeout = timeout < 0 ? DEFAULT_TIMEOUT : timeout * 1000;

        Boolean canLock = false;
        long start = System.currentTimeMillis();
        try {
            do {
                if (doAcquire(lockKey, requestId, expireTime)) {
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(100);
            } while (System.currentTimeMillis() - start < timeout);

        } catch (InterruptedException e) {
        }
        return canLock;
    }

    protected abstract boolean doAcquire(String lockKey, String requestId, int expireTime);


}
