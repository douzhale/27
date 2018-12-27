package com.cx.demo.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cx.security")
public class SecurityProperties {

    private BrowserProperties browser=new BrowserProperties();

    private ValidataCodeProperties code=new ValidataCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidataCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidataCodeProperties code) {
        this.code = code;
    }
}
