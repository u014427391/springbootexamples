package com.example.springcloud.hystrix.component;

import com.example.springcloud.hystrix.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  FeignHystrixClientFallback
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/03 09:58  修改内容:
 * </pre>
 */
@Slf4j
@Component
public class FeignHystrixClientFallback implements FeignHystrixClient {

    @Override
    public User findGithubUser(String username) {
        log.info("fallback方法，接收的参数：username = {}",username);
        User user = new User();
        user.setName("defaultUser");
        user.setBlog("https://smilenicky.blog.csdn.net");
        return user;
    }
}
