package com.example.jedis.common;

import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import static com.example.jedis.common.RedisConstant.DEFAULT_EXPIRE;
import static com.example.jedis.common.RedisConstant.DEFAULT_TIMEOUT;

@Slf4j
public abstract class AbstractDistributedLock implements DistributedLock {

    @Override
    public boolean acquire(String lockKey, String requestId, int expireTime, int timeout) {
        expireTime = expireTime <= 0 ? DEFAULT_EXPIRE : expireTime;
        timeout = timeout < 0 ? DEFAULT_TIMEOUT : timeout * 1000;

        long start = System.currentTimeMillis();
        try {
            do {
                if (doAcquire(lockKey, requestId, expireTime)) {
                    watchDog(lockKey, requestId, expireTime);
                    return true;
                }
                TimeUnit.MILLISECONDS.sleep(100);
            } while (System.currentTimeMillis() - start < timeout);

        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SocketTimeoutException) {
                // ignore exception
                log.error("sockTimeout exception:{}", e);
            }
            else if (cause instanceof  InterruptedException) {
                // ignore exception
                log.error("Interrupted exception:{}", e);
            }
            else {
                log.error("lock acquire exception:{}", e);
            }
            throw new LockException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean release(String lockKey, String requestId) {
        try {
            return doRelease(lockKey, requestId);
        } catch (Exception e) {
            log.error("lock release exception:{}", e);
            throw new LockException(e.getMessage(), e);
        }
    }

    protected abstract boolean doAcquire(String lockKey, String requestId, int expireTime);

    protected abstract boolean doRelease(String lockKey, String requestId);

    protected abstract void watchDog(String lockKey, String requestId, int expireTime);


}
