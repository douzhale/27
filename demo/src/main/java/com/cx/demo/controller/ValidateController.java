package com.cx.demo.controller;


import com.cx.demo.entity.ImageCode;
import com.cx.demo.entity.SmsCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 前台调用此接口获取验证码的图片 和Code
 * 把Code存入session
 */
@RestController
public class ValidateController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String SMS_SESSION_KEY="SESSION_KEY_SMS_CODE";




    @GetMapping("/code/image")
    public void creatCode(HttpServletRequest request, HttpServletResponse response){
        ImageCode imageCode = generate();
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        try {
            ImageIO.write(imageCode.getBufferedImage(),"JPG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    @GetMapping("/code/smsCode")
    public void creatSmsCode(HttpServletRequest request, HttpServletResponse response){
        String code = getSmsCode();
        SmsCode smsCode = new SmsCode(code, 120);
        sessionStrategy.setAttribute(new ServletWebRequest(request),SMS_SESSION_KEY,smsCode);
        logger.info("手机验证码为:"+code);
    }


    private ImageCode generate() {
        int width = 64;
        int height = 32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, 60);

    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }




    private String getSmsCode(){
        return ((int)(Math.random()*9+1)*1000)+"";
    }




}
