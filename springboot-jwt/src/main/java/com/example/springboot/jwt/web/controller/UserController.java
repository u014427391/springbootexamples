package com.example.springboot.jwt.web.controller;

import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.jwt.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *  UserController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/07 14:14  修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    JWTProperties jwtProperties;
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @GetMapping("/auth-info")
    public ResponseEntity authInfo(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtProperties.getTokenKey());
        String token = authHeader.substring(7);
        return ResponseEntity.ok(jwtTokenUtil.getUserIdFromClaims(token));
    }
}
