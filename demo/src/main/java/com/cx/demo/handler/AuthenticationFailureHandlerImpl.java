package com.cx.demo.handler;


import com.cx.demo.properties.BrowserProperties;
import com.cx.demo.properties.LoginProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationFailureHandler")
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private BrowserProperties browserProperties;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(browserProperties.getType().equals(LoginProperties.JSON.toString())){
            logger.info("失败了");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
