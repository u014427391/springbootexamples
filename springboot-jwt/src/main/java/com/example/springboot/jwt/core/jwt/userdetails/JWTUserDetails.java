package com.example.springboot.jwt.core.jwt.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

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
@NoArgsConstructor
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
    private  Boolean isAccountNonExpired = false;
    /**
     * 账户是否锁定
     */
    private  Boolean isAccountNonLocked = false;
    /**
     * 密码是否过期
     */
    private  Boolean isCredentialsNonExpired = false;
    /**
     * 账号是否激活
     */
    private  Boolean isEnabled = true;
    /**
     * 上次密码重置时间
     */
    private  Instant lastPasswordResetDate;

    public JWTUserDetails(Long id, String username, String password, List<GrantedAuthority> mapToGrantedAuthorities) {
        this.userId = id;
        this.username = username;
        this.password = password;
        this.authorities = mapToGrantedAuthorities;
    }

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

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


}
