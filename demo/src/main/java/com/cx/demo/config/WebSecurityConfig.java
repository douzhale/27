package com.cx.demo.config;

import com.cx.demo.handler.AuthenticationFailureHandlerImpl;
import com.cx.demo.handler.AuthenticationSuccessHandlerImpl;
import com.cx.demo.properties.BrowserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private static final Logger loger= LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private BrowserProperties browserProperties;

    @Autowired
    AuthenticationFailureHandlerImpl authenticationFailureHandler;

    @Autowired
    AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/auth")
                .loginProcessingUrl("/user/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth",browserProperties.getLoginUrl()).permitAll()
                .anyRequest()
                .authenticated()
                 .and()
                .csrf()
                .disable();
    }
}
