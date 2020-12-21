package com.example.springboot.oauth2.service;

import com.example.springboot.oauth2.entity.User;
import com.example.springboot.oauth2.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/30 15:15  修改内容:
 * </pre>
 */
@Slf4j
@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        /*if(user == null){
            //log.info("登录用户{}，用户名或密码错误!",username);
            throw new UsernameNotFoundException("登录用户["+username + "]用户名或密码错误!");
        }*/
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return Arrays.asList(Collections.emptyList());
    }
}
