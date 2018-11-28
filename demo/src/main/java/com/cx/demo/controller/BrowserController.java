package com.cx.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserController {

    Logger logger= LoggerFactory.getLogger(BrowserController.class);

     private RequestCache requestCache=new HttpSessionRequestCache();

     private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @GetMapping("auth")
    public String getUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        SavedRequest request = requestCache.getRequest(httpServletRequest, httpServletResponse);
       if(request!=null){
           String redirectUrl = request.getRedirectUrl();
           logger.info("请求地址是="+redirectUrl);
           if(StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
               try {
                   redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,redirectUrl);
               } catch (IOException e) {

               }
           }


       }
       return "登陆有问题";
    }

}
