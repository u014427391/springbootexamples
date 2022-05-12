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
        http.formLogin().usernameParameter("username").passwordParameter("password").loginPage("/login").permitAll()
            .and().csrf().disable()
            .authorizeRequests().antMatchers("/login/**", "/logout/**","/report/**").permitAll()
            .anyRequest().authenticated()
            .and().headers().addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO))
            .xssProtection()
            .and().contentSecurityPolicy("form-action 'self'; report-uri /report; report-to csp-violation-report");
    }

}
