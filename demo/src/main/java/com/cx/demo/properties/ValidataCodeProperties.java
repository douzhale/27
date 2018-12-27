package com.cx.demo.properties;

import org.springframework.stereotype.Component;

@Component
public class ValidataCodeProperties {

    private ImageCodeProperties image=new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
