package com.cx.demo.filter;

import com.cx.demo.ValidateCodeException;
import com.cx.demo.bean.MyAuthenticationServiceException;
import com.cx.demo.controller.ValidateController;
import com.cx.demo.entity.ImageCode;
import com.cx.demo.handler.AuthenticationFailureHandlerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


/**
 * 这里是需要验证验证码
 * 1.是否与传回来的imgCode相同
 * 2.是否超时
 * 3.不管有没有成功需清除session里面的imgCode
 * Filter是加在验证username和password之前的,所以需要判断是否是登录请求
 *
 *
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    private AuthenticationFailureHandlerImpl authenticationFailureHandler;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
      if(StringUtils.equals("/user/login",httpServletRequest.getRequestURI())&&StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
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
        ImageCode imageCode=(ImageCode)sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY);//session里面获取的
        String imgCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imgCode");//请求传入的参数
        if(StringUtils.isEmpty(imgCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(imgCode==null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(imageCode.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
            throw new ValidateCodeException("验证码已经超时");
        }

        if(!StringUtils.equals(imageCode.getCode(),imgCode)){
            throw new ValidateCodeException("验证码输入错误");
        }
        sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
    }

    public AuthenticationFailureHandlerImpl getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandlerImpl authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
