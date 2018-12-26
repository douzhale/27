package com.cx.demo.filter;

import com.cx.demo.ValidateCodeException;
import com.cx.demo.controller.ValidateController;
import com.cx.demo.entity.ImageCode;
import com.cx.demo.entity.SmsCode;
import com.cx.demo.handler.AuthenticationFailureHandlerImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("smsCodeFilter")
public class SmsCodeFilter  extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    private AuthenticationFailureHandlerImpl authenticationFailureHandler;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equals("/login/smsCode",httpServletRequest.getRequestURI())&&StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
            try {
                // 1. 进行验证码的校验
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                // 2. 如果校验不通过，调用SpringSecurity的校验失败处理器
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }

        // 3. 校验通过，就放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    public void validate(ServletWebRequest request) throws ServletRequestBindingException {
        SmsCode smsCode=(SmsCode)sessionStrategy.getAttribute(request, ValidateController.SMS_SESSION_KEY);//session里面获取的
        String code = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");//请求传入的参数
        if(StringUtils.isEmpty(code)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(code==null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(smsCode.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateController.SMS_SESSION_KEY);
            throw new ValidateCodeException("验证码已经超时");
        }

        if(!StringUtils.equals(smsCode.getSmsCode(),code)){
            throw new ValidateCodeException("验证码输入错误");
        }
        sessionStrategy.removeAttribute(request,ValidateController.SMS_SESSION_KEY);
    }

    public AuthenticationFailureHandlerImpl getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandlerImpl authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
