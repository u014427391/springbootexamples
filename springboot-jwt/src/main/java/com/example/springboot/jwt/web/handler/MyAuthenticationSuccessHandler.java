package com.example.springboot.jwt.web.handler;

import com.alibaba.fastjson.JSON;
import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.userdetails.JWTUserDetails;
import com.example.springboot.jwt.util.JWTTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <pre>
 *  MyAuthenticationSuccessHandler
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/07 18:09  修改内容:
 * </pre>
 */
@Slf4j

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_ACCOUNT_ENABLED = "enabled";
    private static final String CLAIM_KEY_ACCOUNT_NON_LOCKED = "non_locked";
    private static final String CLAIM_KEY_ACCOUNT_NON_EXPIRED = "non_expired";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = new JWTUserDetails();
        ((JWTUserDetails) userDetails).setUserId(1L);
        ((JWTUserDetails) userDetails).setUsername("123123");
        ((JWTUserDetails) userDetails).setIsEnabled(true);
        ((JWTUserDetails) userDetails).setIsAccountNonExpired(false);
        String token = generateToken(userDetails);
        httpServletResponse.setHeader("Authorization","Bearer-"+token);
    }

    /**
     * 生成acceptToken
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        JWTUserDetails user = (JWTUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        return generateToken(user.getUsername(),claims);
    }

    /**
     * 生成acceptToken
     * @param userId
     * @param claims
     * @return
     */
    public String generateToken(String userId, Map<String, Object> claims) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(3600L))
                .signWith(SignatureAlgorithm.HS256, "mysecury")
                .compact();
    }

    /**
     * 生成失效时间
     * @param expiration
     * @return
     */
    public Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 生成Claims
     * @Param user
     * @return
     */
    public Map<String, Object> generateClaims(JWTUserDetails user) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, user.getUserId());
        claims.put(CLAIM_KEY_ACCOUNT_ENABLED, user.isEnabled());
        claims.put(CLAIM_KEY_ACCOUNT_NON_LOCKED, user.isAccountNonLocked());
        claims.put(CLAIM_KEY_ACCOUNT_NON_EXPIRED, user.isAccountNonExpired());
        if (!CollectionUtils.isEmpty(user.getAuthorities())) {
            claims.put(CLAIM_KEY_AUTHORITIES , JSON.toJSON(getAuthorities(user.getAuthorities())));
        }
        return claims;
    }

    /**
     * 获取角色权限
     * @param authorities
     * @return
     */
    public List<String> getAuthorities(Collection<? extends GrantedAuthority> authorities){
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

}
