package com.example.springboot.jwt.configuration;


import com.example.springboot.jwt.core.encode.CustomPasswordEncoder;
import com.example.springboot.jwt.web.filter.JWTAuthenticationTokenFilter;
import com.example.springboot.jwt.web.handler.JWTAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 *  SecurityConfiguration
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/30 15:58  修改内容:
 * </pre>
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("jwtUserService")
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new CustomPasswordEncoder());
        auth.parentAuthenticationManager(authenticationManagerBean());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/asserts/**");
        web.ignoring().antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http   // 配置登录页并允许访问
                .formLogin().loginPage("/login").permitAll()
                // 登录成功被调用
                //.successHandler(new MyAuthenticationSuccessHandler())
                // 配置登出页面
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**","/authenticate/**").permitAll()
                // 其余所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 自定义authenticationEntryPoint
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint )
                // 不使用Session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 关闭跨域保护;
                .and().csrf().disable();
        // JWT 过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }



    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
