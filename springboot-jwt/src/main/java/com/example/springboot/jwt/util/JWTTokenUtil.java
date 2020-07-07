package com.example.springboot.jwt.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.example.springboot.jwt.configuration.JWTProperties;
import com.example.springboot.jwt.core.userdetails.JWTUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * <pre>
 *   JWT工具类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/06 13:57  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class JWTTokenUtil {

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_ACCOUNT_ENABLED = "enabled";
    private static final String CLAIM_KEY_ACCOUNT_NON_LOCKED = "non_locked";
    private static final String CLAIM_KEY_ACCOUNT_NON_EXPIRED = "non_expired";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    //签名方式
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    @Autowired
    JWTProperties jwtProperties;

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
                .setExpiration(generateExpirationDate(jwtProperties.getExpiration().toMillis()))
                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 校验acceptToken
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        JWTUserDetails user = (JWTUserDetails) userDetails;
       return validateToken(token, user.getUsername());
    }

    /**
     * 校验acceptToken
     * @param token
     * @param  username
     * @return
     */
    public boolean validateToken(String token, String username) {
        try {
            final String userId = getUserIdFromClaims(token);
            return getClaimsFromToken(token) != null
                    && userId.equals(username)
                    && !isTokenExpired(token);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Token!"+e);
        }
    }

    /**
     * 校验acceptToken
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            return getClaimsFromToken(token) != null
                    && !isTokenExpired(token);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Token!"+e);
        }
    }

    /**
     * 解析token 信息
     * @param token
     * @return
     */
    public Claims  getClaimsFromToken(String token){
        Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        return claims;
    }

    /**
     * 从token获取userId
     * @param token
     * @return
     */
    public String getUserIdFromClaims(String token) {
        String userId = getClaimsFromToken(token).getSubject();
        return userId;
    }

    /**
     * 从token获取ExpirationDate
     * @param token
     * @return
     */
    public Date getExpirationDateFromClaims(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration;
    }

    /**
     * token 是否过期
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromClaims(token);
        return expirationDate.before(new Date());
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
