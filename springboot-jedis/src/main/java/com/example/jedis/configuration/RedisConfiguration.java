package com.example.jedis.configuration;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass({GenericObjectPool.class, JedisConnection.class, Jedis.class})
@EnableRedisRepositories(basePackages = "com.example.jedis.repository")
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
public class RedisConfiguration {


    private final RedisProperties properties;


    public RedisConfiguration(RedisProperties properties) {
        this.properties = properties;
    }

    /**
     *
     * testOnBorrow: 向资源池借用连接时是否做连接有效性检测（ping）
     * testOnReturn: 向资源池归还连接时是否做连接有效性检测（ping）
     * testWhileIdle: 是否在空闲资源监测时通过ping命令监测连接有效性，无效连接将被销毁。
     * jmxEnabled: 是否开启JMX监控
     * @Date 2023/12/11 16:29
     * @return redis.clients.jedis.JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        RedisProperties.Pool pool = properties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMinIdle(pool.getMinIdle());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(60000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setNumTestsPerEvictionRun(-1);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        jedisPoolConfig.setBlockWhenExhausted(false);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setJmxEnabled(true);
        return jedisPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration jedisConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setDatabase(properties.getDatabase());
        config.setPassword(RedisPassword.of(properties.getPassword()));
        return config;
    }


    @Bean
    public JedisPool jedisPool() {
        JedisPool jedisPool = new JedisPool(jedisPoolConfig(),properties.getHost(),  properties.getPort(),
                Convert.toInt(properties.getTimeout().getSeconds()));
        return jedisPool;
    }


    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisConfig());
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }





}
