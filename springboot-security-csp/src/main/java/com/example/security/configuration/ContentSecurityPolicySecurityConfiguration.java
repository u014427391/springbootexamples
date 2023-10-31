package com.example.security.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class ContentSecurityPolicySecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"http://localhost:8080/report\"}]}";

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/asserts/**");
        web.ignoring().antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义一个登录页面，需要写一个/login接口来跳转，
        // usernameParameter指定自定义的login.html页面的input标签用户密码属性
        http.formLogin().usernameParameter("username").passwordParameter("password").loginPage("/login").permitAll()
            // 关闭跨域保护;
            .and().csrf().disable()
            // 开放权限，不需要登录也可以访问
            .authorizeRequests().antMatchers("/login/**", "/logout/**","/report/**").permitAll()
            // 其它的请求都需要登录验证
            .anyRequest().authenticated()
            // 设置Report-To，发生一些比如CSP拦截，会发送报告到自己定义的report接口
            .and().headers().addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO))
            // 设置xss防护
            .xssProtection()
            // 设置CSP内容安全策略
            .and().contentSecurityPolicy("form-action 'self'; report-uri /report; report-to csp-violation-report");
    }

}
