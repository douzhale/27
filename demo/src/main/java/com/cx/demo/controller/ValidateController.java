package com.cx.demo.controller;


import com.cx.demo.entity.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台调用此接口获取验证码的图片 和Code
 * 吧Code存入session
 */
@RestController
public class ValidateController {


    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();





    @GetMapping("/code/image")
    public void creatCode(HttpServletRequest request, HttpServletResponse response){



    }


    public ImageCode createImageCode(){



        return new ImageCode();
    }
}
