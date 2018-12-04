package com.cx.demo.bean;

import org.springframework.security.authentication.AuthenticationServiceException;

public class MyAuthenticationServiceException extends AuthenticationServiceException {
    public MyAuthenticationServiceException(String msg) {
        super(msg);
    }

    public MyAuthenticationServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}
