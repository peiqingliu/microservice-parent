package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso  //开启
public class SsoClient2Application {
    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Application.class,args);
    }
}
