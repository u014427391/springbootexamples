package com.example.springboot.jwt.configuration;

import com.example.springboot.jwt.core.io.support.YamlPropertyResourceFactory;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * <pre>
 *  JWT配置类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/06 11:37  修改内容:
 * </pre>
 */
@Component
@PropertySource(value = "classpath:jwt.yml",encoding = "utf-8",factory = YamlPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "jwt")
@Data
@ToString
public class JWTProperties {

    /**
     * 存放Token的Header key值
     */
    private String tokenKey;

    /*
     * 自定义密钥，加盐
     */
    private String secret;

    /*
     * 超时时间 单位秒
     */
    private Duration expiration =Duration.ofMinutes(3600);

    /*
     * 自定义token 前缀字符
     */
    private String tokenPrefix;

    /*
     * accessToken超时时间 单位秒
     */
    private Duration accessToken =Duration.ofMinutes(3600);

    /*
     * 刷新token时间 单位秒
     */
    private Duration refreshToken =Duration.ofMinutes(3600);

    /*
     * 允许访问的uri
     */
    private String permitAll;

    /*
     * 需要校验的uri
     */
    private String authenticateUri;
}
