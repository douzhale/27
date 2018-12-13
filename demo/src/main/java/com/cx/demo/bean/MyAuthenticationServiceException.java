package com.cx.demo.bean;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationServiceException extends AuthenticationException {


    private static final long serialVersionUID = -7285211528095468156L;

    public MyAuthenticationServiceException(String msg) {
        super(msg);
    }
}
