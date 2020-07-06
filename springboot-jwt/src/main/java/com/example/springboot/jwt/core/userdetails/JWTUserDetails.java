package com.example.springboot.jwt.core.userdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

/**
 * <pre>
 *  JWTUserDetails
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/06 14:45  修改内容:
 * </pre>
 */
@Data
@AllArgsConstructor
public class JWTUserDetails implements UserDetails {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户角色权限
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * 账号是否过期
     */
    private final Boolean isAccountNonExpired;
    /**
     * 账户是否锁定
     */
    private final Boolean isAccountNonLocked;
    /**
     * 密码是否过期
     */
    private final Boolean isCredentialsNonExpired;
    /**
     * 账号是否激活
     */
    private  Boolean isEnabled;
    /**
     * 上次密码重置时间
     */
    private final Instant lastPasswordResetDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


}
