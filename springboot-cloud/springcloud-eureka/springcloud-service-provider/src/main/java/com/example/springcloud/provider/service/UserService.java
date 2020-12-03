package com.example.springcloud.provider.service;

import com.example.springcloud.provider.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *  UserService，查找github用户数据
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/27 17:42  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public User findUser(String user) throws InterruptedException {
        log.info("username[{}]" , user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        return results;
    }
}
