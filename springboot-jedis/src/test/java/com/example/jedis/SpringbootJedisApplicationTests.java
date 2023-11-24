package com.example.jedis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import com.example.jedis.common.JedisTemplate;
import com.example.jedis.configuration.RedisConfiguration;
import com.example.jedis.model.UserDto;
import com.example.jedis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
@ContextConfiguration(classes = RedisConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class SpringbootJedisApplicationTests {

    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JedisTemplate jedisTemplate;

    @Test
    void contextLoads() {
        Jedis jedis= jedisPool.getResource();
        jedis.set("tKey","你好呀");
        jedis.close();

    }

    @Test
    void testRedisTemplate() {
        redisTemplate.opsForValue().set("rtKey","你好呀");
    }

    @Test
    void testCrud() {
        IntStream.range(0,100000).forEach(e->{
            final UserDto userDto = UserDto.builder()
                    .id(IdUtil.getSnowflake().nextId())
                    .name("用户1")
                    .gender(UserDto.Gender.MALE)
                    .build();
            userRepository.save(userDto);
        });
    }

    @Test
    void testJedisTemplate() {
        String tKey = jedisTemplate.get("tKey");
        System.out.println(tKey);
    }


    @Test
    void testKeys() {
        TimeInterval timeInterval = DateUtil.timer();
        Set<String> setData = jedisTemplate.keys("user:*");
        System.out.println("keys use:"+timeInterval.intervalRestart()+"ms");
        Set<String> setDataScan = jedisTemplate.scan("user:*");
        System.out.println("scan use:"+timeInterval.intervalRestart()+"ms");
    }

    @Test
    void testScan() {
        TimeInterval timeInterval = DateUtil.timer();
        Set<String> setData = jedisTemplate.scan("user:*");
        System.out.println(timeInterval.intervalRestart());

    }




}
