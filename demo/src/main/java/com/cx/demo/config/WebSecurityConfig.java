package com.cx.demo.config;

import com.cx.demo.filter.ValidateCodeFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;


    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter =new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/auth")
//                .loginProcessingUrl("/user/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth",browserProperties.getLoginUrl(),"/code/image","/code/smsCode").permitAll()
                .anyRequest()
                .authenticated()
                 .and()
                .csrf()
                .disable().apply(smsCodeAuthenticationSecurityConfig);
    }
}
