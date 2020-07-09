package com.example.springboot.jwt.core.jwt.factory;

import com.example.springboot.jwt.core.jwt.userdetails.JWTUserDetails;
import com.example.springboot.jwt.model.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  JWTUserDetailsFactory
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/09 13:23  修改内容:
 * </pre>
 */
public class JWTUserDetailsFactory {

    public static JWTUserDetails create(UserDto user) {
        return new JWTUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles())
                );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
