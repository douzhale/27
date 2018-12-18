package com.cx.demo.filter;

import com.cx.demo.SmsCodeAuthenticationToken;
import com.cx.demo.ValidateCodeException;
import com.cx.demo.controller.ValidateController;
import com.cx.demo.entity.ImageCode;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import sun.security.util.SecurityConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobilePhone";

    private boolean postOnly = true;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/smsCode", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobilePhone = this.obtainMobile(request);
            mobilePhone = mobilePhone.trim();
            /**
             * 判断手机的验证码是否正确
             */
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobilePhone);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }



    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    /**
     * 获取手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_MOBILE_KEY);
    }


    public void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode imageCode=(ImageCode)sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY);//session里面获取的
        String imgCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imgCode");//请求传入的参数
        if(org.apache.commons.lang.StringUtils.isEmpty(imgCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(imgCode==null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(imageCode.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
            throw new ValidateCodeException("验证码已经超时");
        }

        if(!org.apache.commons.lang.StringUtils.equals(imageCode.getCode(),imgCode)){
            throw new ValidateCodeException("验证码输入错误");
        }
        sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
    }

}
