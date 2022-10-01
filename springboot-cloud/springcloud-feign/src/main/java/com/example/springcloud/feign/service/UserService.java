package com.example.springcloud.feign.service;

import com.example.springcloud.feign.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <pre>
 *  UserService
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/28 13:09  修改内容:
 * </pre>
 */
@FeignClient(value = "EUREKA-SERVICE-PROVIDER" ,contextId = "EUREKA-SERVICE-PROVIDER", url = "http://127.0.0.1:8083")
@Service
public interface UserService {
    @RequestMapping(value = "/api/users/{username}",method = RequestMethod.GET)
    User findGithubUser(@PathVariable("username") String username);

    @GetMapping(value = "/api/users")
    List<User> list(@SpringQueryMap User user);

}
