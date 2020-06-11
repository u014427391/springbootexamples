package com.example.oauth2.password.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <pre>
 *  用户信息控制类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/06/11 14:09  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/userinfo")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
