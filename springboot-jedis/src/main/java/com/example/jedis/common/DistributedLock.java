package com.example.jedis.common;

public interface DistributedLock {

    default boolean acquire(String lockKey, String requestId) {
        return acquire(lockKey, requestId, RedisConstant.DEFAULT_EXPIRE);
    }

    default boolean acquire(String lockKey, String requestId, int expireTime) {
        return acquire(lockKey, requestId, expireTime, RedisConstant.DEFAULT_TIMEOUT);
    }

    boolean acquire(String lockKey, String requestId, int expireTime, int timeout);

    boolean release(String lockKey, String requestId);

}
