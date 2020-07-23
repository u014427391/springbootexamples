package com.example.springboot.jwt.web.controller;

import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.jwt.userdetails.JWTUserDetails;
import com.example.springboot.jwt.core.jwt.util.JWTTokenUtil;
import com.example.springboot.jwt.model.dto.UserDto;
import com.example.springboot.jwt.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *  LoginController
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/06 18:11  修改内容:
 * </pre>
 */
@Controller
@CrossOrigin
public class LoginController {

    @Autowired
    JWTTokenUtil jwtTokenUtil;
    @Autowired
    JWTProperties jwtProperties;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value = "/authenticate")
    @ResponseBody
    public ResponseEntity<?> authenticate( UserDto userDto, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        //authenticate(userDto.getUsername(), userDto.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        response.setHeader(jwtProperties.getTokenKey(),jwtProperties.getTokenPrefix()+token);
        return ResponseEntity.ok(token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @GetMapping(value = {"/index"})
    public ModelAndView toIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","This is <b>great!</b>");
        modelAndView.setViewName("index");
        return modelAndView;
    }


}
