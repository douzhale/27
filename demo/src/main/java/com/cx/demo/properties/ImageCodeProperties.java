package com.cx.demo.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


@EnableConfigurationProperties
@ConfigurationProperties(prefix = "img.security")
public class ImageCodeProperties {

    private String width="200";
    private String height="100";
    private String time="60";
    private String url="/usr/a";

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
