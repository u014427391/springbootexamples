package com.example.springcloud.hystrix.controller;

import com.example.springcloud.hystrix.bean.User;
import com.example.springcloud.hystrix.component.FeignHystrixClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *      RestController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/01 16:59  修改内容:
 * </pre>
 */
@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    FeignHystrixClient feignHystrixClient;

    /**
     * @HystrixCommand注解指定异常时调用的方法
     * @Author mazq
     * @Date 2020/08/01 18:17
     * @Param [username]
     * @return
     */
    @GetMapping("/findUser/{username}")
    @HystrixCommand(fallbackMethod = "userApiFallback")
    public User index(@PathVariable("username")String username){
        return restTemplate.getForObject("http://EUREKA-SERVICE-PROVIDER/api/users/"+username,User.class);
    }

    public User userApiFallback(String username) {
        log.info("fallback方法，接收的参数：username = {}",username);
        User user = new User();
        user.setName("defaultUser");
        user.setBlog("https://smilenicky.blog.csdn.net");
        return user;
    }


    @GetMapping("/feign/findUser/{username}")
    public User findGithubUser(@PathVariable("username")String username){
        return feignHystrixClient.findGithubUser(username);
    }

}
