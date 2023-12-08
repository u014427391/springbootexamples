package com.example.jedis;

import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class SpringbootJedisApplication {

    @Resource
    RedisConnectionFactory factory;


    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("springbootJedis");
        SpringApplication.run(SpringbootJedisApplication.class, args);
        stopWatch.stop();
        log.info("Springboot项目启动成功时间:{}ms \n", stopWatch.getTotalTimeMillis());
        log.info(stopWatch.prettyPrint());
    }

    @PreDestroy
    public void flushDB() {
        factory.getConnection().flushDb();
    }

}
