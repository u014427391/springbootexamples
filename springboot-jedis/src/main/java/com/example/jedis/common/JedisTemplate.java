package com.example.jedis.common;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;


@Component
@Slf4j
public class JedisTemplate implements InitializingBean {


    @Value("${example.redis.scan.count:300}")
    private static Integer scount;

    @Resource
    private JedisPool jedisPool;

    private Jedis jedis;

    public JedisTemplate(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisTemplate() {

    }

    @Override
    public void afterPropertiesSet() {
        jedis = jedisPool.getResource();
    }


    public <T> T execute(Function<Jedis, T> action) {
        T apply = null;
        try {
            jedis = jedisPool.getResource();
            apply = action.apply(jedis);
        } catch (JedisException e) {
            handleException(e);
            throw e;
        } finally {
            jedis.close();
        }
        return apply;
    }

    public void execute(Consumer<Jedis> action) {
        try {
            jedis = jedisPool.getResource();
            action.accept(jedis);
        } catch (JedisException e) {
            handleException(e);
            throw e;
        } finally {
            jedis.close();
        }
    }

    public JedisPool getJedisPool() {
        return this.jedisPool;
    }

    public void set(final String key, final String value) {
        execute(e -> {
            jedis.set(key, value);
        });
    }

    public String get(final String key) {
        return execute(e -> {
            return jedis.get(key);
        });
    }


    public void mset(final String... keysvalues) {
        execute(e -> {
            return jedis.mset(keysvalues);
        });
    }

    public List<String> mget(final String... keys) {
        return execute(e -> {
            return jedis.mget(keys);
        });
    }

    public Boolean exists(final String key) {
        return execute(e->{
            return jedis.exists(key);
        });
    }

    public Boolean setnxex(final String key, final String value, int seconds) {
        return execute(e->{
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(seconds);
            return isStatusOk(jedis.set(key, value, setParams));
        });
    }

    public Boolean setnx(final String key, final String value) {
        return execute(e->{
            return jedis.setnx(key, value) == 1l;
        });
    }

    public void setex(final String key, final String value, final int seconds) {
        execute(e->{
            jedis.setex(key, seconds, value);
        });
    }

    public Long strlen(final String key) {
        return execute(e -> {
            return jedis.strlen(key);
        });
    }

    public Long incr(final String key) {
        return execute(e -> {
            return jedis.incr(key);
        });
    }

    public Long incrBy(final String key, final long increment) {
        return execute(e->{
            return jedis.incrBy(key, increment);
        });
    }

    public Double incrByFloat(final String key, final double increment) {
        return execute(e->{
            return jedis.incrByFloat(key, increment);
        });
    }

    public void hset(final String key, final String fieldName, final String value) {
        execute(e->{
            jedis.hset(key, fieldName, value);
        });
    }

    public void hmset(final String key, final Map<String, String> map) {
        execute(e->{
            jedis.hmset(key, map);
        });
    }

    public Boolean hsetnx(final String key, final String fieldName, final String value) {
        return execute(e->{
            return jedis.hsetnx(key, fieldName, value) == 1L;
        });
    }

    public String hget(final String key, final String fieldName) {
        return execute(e->{
            return jedis.hget(key, fieldName);
        });
    }

    public List<String> hmget(final String key, final String... fieldsNames) {
        return execute(e->{
            return jedis.hmget(key, fieldsNames);
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(e->{
            return jedis.hgetAll(key);
        });
    }



    public Boolean lpush(final String key, final String values) {
        return execute(e -> {
            return jedis.lpush(key, values) == 1L;
        });
    }

    public Boolean rpush(final String key, final String values) {
        return execute(e -> {
            return jedis.rpush(key, values) == 1L;
        });
    }

    public String lpop(final String key) {
        return execute(e -> {
            return jedis.lpop(key);
        });
    }

    public String rpop(final String key) {
        return execute(e -> {
            return jedis.rpop(key);
        });
    }

    public Boolean sadd(final String key, final String members) {
        return execute(e->{
            return jedis.sadd(key, members) == 1L;
        });
    }

    public Set<String> smembers(final String key) {
        return execute(e->{
            return jedis.smembers(key);
        });
    }

    public Long scard(final String key) {
        return execute(e->{
            return jedis.scard(key);
        });
    }

    public Boolean zadd(final String key, final double score, final String member) {
        return execute(e->{
            return jedis.zadd(key, score, member) == 1L;
        });
    }

    public Boolean zadd(final String key, final Map<String, Double> scoreMembers) {
        return execute(e->{
            return jedis.zadd(key, scoreMembers) == 1L;
        });
    }

    public Set<String> zrange(final String key, final int start, final int end) {
        return execute(e->{
            return jedis.zrange(key, start, end);
        });
    }

    public Set<String> zrevrange(final String key, final int start, final int end) {
        return execute(e->{
            return jedis.zrevrange(key, start, end);
        });
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return execute(e->{
            return jedis.zrangeByScore(key, min, max);
        });
    }

    public Double zincrby(final String key, final Double score, final String member) {
        return execute(e->{
            return jedis.zincrby(key, score, member);
        });
    }

    public Long zcount(final String key, final double min, final double max) {
        return execute(e->{
            return jedis.zcount(key, min, max);
        });
    }

    public Boolean expire(final String key, final int seconds) {
        return execute(e->{
            return jedis.expire(key, seconds) == 1L;
        });
    }

    public Long ttl(final String key) {
        return execute(e->{
            return jedis.ttl(key);
        });
    }

    public Set<String> keys(final String pattern) {
        return execute(e->{
            return jedis.keys(pattern);
        });
    }

    public Set<String> scan(String pattern) {
        return execute(e->{
            return this.doScan(pattern);
        });
    }


    protected Set<String> doScan(String pattern) {
        Set<String> resultSet = new ConcurrentHashSet<>();
        String cursor = String.valueOf(0);
        try {
            do {
                ScanParams params = new ScanParams();
                params.count(300);
                params.match(pattern);
                ScanResult<String> scanResult = jedis.scan(cursor, params);
                cursor = scanResult.getCursor();
                resultSet.addAll(scanResult.getResult());
            } while (Integer.valueOf(cursor) > 0);
        } catch (NumberFormatException e) {
            log.error("doScan NumberFormatException:{}", e);
        } catch (Exception e) {
            log.error("doScan Exception :{}", e);
        }
        return resultSet;
    }


    protected void handleException(JedisException e) {
        if (e instanceof JedisConnectionException) {
            log.error("redis connection exception:{}", e);
        } else if (e instanceof JedisDataException) {
            log.error("jedis data exception:{}", e);
        } else {
            log.error("jedis exception:{}", e);
        }
    }

    protected static boolean isStatusOk(String status) {
        return status != null && ("OK".equals(status) || "+OK".equals(status));
    }

}
