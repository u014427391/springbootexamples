package com.example.springboot.oauth2.component;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <pre>
 *  自定义AccessTokenConverter
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/06/15 13:40  修改内容:
 * </pre>
 */
@Component("jdbcAccessTokenConverter")
public class JdbcAccessTokenConverter implements AccessTokenConverter {

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    /**
     * @param tokenConverter the tokenConverter to set
     */
    public void setAccessTokenConverter(AccessTokenConverter tokenConverter) {
        this.tokenConverter = tokenConverter;
    }

    /**
     * @return the tokenConverter in use
     */
    public AccessTokenConverter getAccessTokenConverter() {
        return tokenConverter;
    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        return tokenConverter.convertAccessToken(oAuth2AccessToken, oAuth2Authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String s, Map<String, ?> map) {
        return tokenConverter.extractAccessToken(s, map);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return tokenConverter.extractAuthentication(map);
    }

    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        String grantType = authentication.getOAuth2Request().getGrantType();
        //授权码和密码模式才自定义token信息
        if("authorization_code".equals(grantType) || "password".equals(grantType)) {
            String userName = authentication.getUserAuthentication().getName();
            // 自定义一些token 信息
            Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>(16);
            additionalInformation.put("user_name", userName);
            additionalInformation = Collections.unmodifiableMap(additionalInformation);
            result.setAdditionalInformation(additionalInformation);
        }
        return result;
    }
}
