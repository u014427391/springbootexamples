package com.example.springcloud.hystrix.component;

import com.example.springcloud.hystrix.bean.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  FeignHystrixClientFallbackFactory
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/03 10:17  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class FeignHystrixClientFallbackFactory implements FallbackFactory<FeignHystrixClient> {

    @Override
    public FeignHystrixClient create(Throwable throwable) {
        return new FeignHystrixClient() {
            @Override
            public User findGithubUser(String username) {
                log.info("fallback方法，接收的参数：username = {}",username);
                log.error("fallback reason：{}", throwable.getMessage());
                User user = new User();
                user.setName("defaultUser");
                user.setBlog("https://smilenicky.blog.csdn.net");
                return user;
            }
        };
    }
}
