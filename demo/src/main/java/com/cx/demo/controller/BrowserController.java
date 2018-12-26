package com.cx.demo.controller;

import com.cx.demo.bean.ExceptionMessage;
import com.cx.demo.entity.SmsCode;
import com.cx.demo.properties.BrowserProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cx.demo.controller.ValidateController.SMS_SESSION_KEY;

@RestController
public class BrowserController {

    Logger logger= LoggerFactory.getLogger(BrowserController.class);

     private RequestCache requestCache=new HttpSessionRequestCache();

     private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

     @Autowired
     private ObjectMapper objectMapper;

     @Autowired
     private ExceptionMessage exceptionMessage;

     @Autowired
     private BrowserProperties browserProperties;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    /**
     * 增加配置地址的功能，如果配置了地址就跳转到配置的地址，没有则是默认地址
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @GetMapping("auth")
    @ResponseBody
    public ExceptionMessage getUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        SavedRequest request = requestCache.getRequest(httpServletRequest, httpServletResponse);
           String redirectUrl = request.getRedirectUrl();
           try {
               if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                   logger.info("url="+ browserProperties.getLoginUrl());
                   redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, browserProperties.getLoginUrl());
               } else {
                   logger.info("走了这一步了");
               }
           }catch (IOException e) {
               e.getMessage();
           }

       exceptionMessage.setContent("登录有问题，请验证身份");
       return exceptionMessage;
    }



    @GetMapping("/code/smsCode")
    public void creatSmsCode(HttpServletRequest request, HttpServletResponse response){
        String code = getSmsCode();
        SmsCode smsCode = new SmsCode(code, 120);
        sessionStrategy.setAttribute(new ServletWebRequest(request),SMS_SESSION_KEY,smsCode);
        logger.info("手机验证码为:"+code);
    }

    private String getSmsCode(){
        return ((int)(Math.random()*9+1)*1000)+"";
    }
}
