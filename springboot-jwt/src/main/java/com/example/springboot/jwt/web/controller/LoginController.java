package com.example.springboot.jwt.web.controller;


import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.userdetails.JWTUserDetails;
import com.example.springboot.jwt.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@RestController
@RequestMapping(value = "jwt")
public class LoginController {

    @Autowired
    JWTProperties jwtProperties;
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @GetMapping("/login")
    public ResponseEntity login( HttpServletResponse response) {
        UserDetails userDetails = new JWTUserDetails();
        ((JWTUserDetails) userDetails).setUserId(1L);
        ((JWTUserDetails) userDetails).setUsername("admin");
        ((JWTUserDetails) userDetails).setPassword("123");
        String token = jwtTokenUtil.generateToken(userDetails);
        response.setHeader(jwtProperties.getHeaderKey(),jwtProperties.getHeaderValuePrefix()+token);
        return ResponseEntity.ok("success");
    }


}
