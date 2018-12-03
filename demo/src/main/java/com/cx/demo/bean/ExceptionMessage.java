package com.cx.demo.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("exceptionMessage")
public class ExceptionMessage implements Serializable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
