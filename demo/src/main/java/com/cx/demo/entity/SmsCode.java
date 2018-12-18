package com.cx.demo.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("smsCode")
public class SmsCode {

    private String mobilePhone;

    private String smsCode;

    private LocalDateTime localDateTime;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    public SmsCode() {
    }

    public SmsCode(String smsCode,int expireTime) {
        this.smsCode = smsCode;
        this.localDateTime = LocalDateTime.now().plusSeconds(expireTime);
    }


    public boolean isExpired(){
        return LocalDateTime.now().isAfter(localDateTime);
    }
}
