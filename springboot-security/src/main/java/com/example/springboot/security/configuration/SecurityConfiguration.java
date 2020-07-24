package com.example.springboot.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 *
 * </pre>
 *      SecurityConfiguration
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/07/24 14:22  修改内容:
 * </pre>
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/asserts/**");
        web.ignoring().antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
        auth.inMemoryAuthentication()
                .withUser("nicky")
                .password(bcryptPasswordEncoder().encode("123"))
                .roles("admin");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http   // 配置登录页并允许访问
                .formLogin().usernameParameter("username").passwordParameter("password").loginPage("/login").permitAll()
                // 配置Basic登录
                //.and().httpBasic()
                // 配置登出页面
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                // 开放接口访问权限，不需要登录授权就可以访问
                .and().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**").permitAll()
                // api接口需要admin管理员才能访问
                .antMatchers("/api/**").hasRole("admin")
                // 其余所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 关闭跨域保护;
                .and().csrf().disable();

        //开启记住我功能，登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        http.rememberMe().rememberMeParameter("remeber");
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
