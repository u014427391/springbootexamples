package com.example.springboot.jwt.service;

import com.example.springboot.jwt.core.userdetails.JWTUserDetails;
import com.example.springboot.jwt.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *  UserDetailsServiceImpl
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/06 18:10  修改内容:
 * </pre>
 */
@Service("jwtUserService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("userMapper")
    UserMapper userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JWTUserDetails user = userRepository.findByUsername(username);
        if(user == null){
            log.info("登录用户[{}]没注册!",username);
            throw new UsernameNotFoundException("登录用户["+username + "]没注册!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
