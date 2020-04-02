package com.example.springboot.cache.config;

public enum CacheNameEnum  {

    /**
     *
     */
    TOKEN_CACHE("usrTkn", 7*24*60*60),
    /**
     * 验证码缓存 5分钟ttl
     */
    SMS_CAPTCHA_CACHE("smsCode", 5*60);
    /**
     * 缓存名称
     */
    private String cacheName;
    /**
     * 缓存过期秒数
     */
    private int ttlSecond;

    CacheNameEnum(String cacheName, int ttlSecond) {
        this.cacheName = cacheName;
        this.ttlSecond = ttlSecond;
    }



    public String cacheName() {
        return this.cacheName;
    }


    public int ttlSecond() {
        return this.ttlSecond;
    }
}