    package com.example.oauth2.implicit.config;


    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.annotation.Order;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.builders.WebSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

    /**
     * <pre>
     *  SpringSecurity配置类
     * </pre>
     *
     * <pre>
     * @author mazq
     * 修改记录
     *    修改后版本:     修改人：  修改日期: 2020/06/11 11:23  修改内容:
     * </pre>
     */
    @Configuration
    @EnableWebSecurity
    @Order(1)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
            auth.inMemoryAuthentication()
                    .withUser("nicky")
                    .password("{noop}123")
                    .roles("admin");
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
                    .formLogin().permitAll()
                    // 配置Basic登录
                    //.and().httpBasic()
                    // 配置登出页面
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                    .and().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**").permitAll()
                    // 其余所有请求全部需要鉴权认证
                    .anyRequest().authenticated()
                    // 关闭跨域保护;
                    .and().csrf().disable();
        }

    }
