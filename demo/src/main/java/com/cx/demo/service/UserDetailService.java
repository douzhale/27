package com.cx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserDetailService implements UserDetailsService {

    private Logger logger= LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名="+username);
        String passWord=passwordEncoder.encode("123456");
        logger.info("加密的密码是="+passWord);
        return new User(username,passWord, AuthorityUtils.commaSeparatedStringToAuthorityList(username));
    }
}
