package com.cx.demo.impl;

import com.cx.demo.entity.ImageCode;
import com.cx.demo.properties.ImageCodeProperties;
import com.cx.demo.util.ValidataCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerator implements ValidataCodeGenerator {

    @Autowired
    private ImageCodeProperties imageCodeProperties;


    @Override
    public void getGeneratorCode() {

    }


}
