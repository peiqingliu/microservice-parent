package com.liupeiqing.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * 跳转到百度路径
 * http://localhost:1022/oauth/authorize?client_id=wuqianqian1&redirect_uri=http://localhost:1023/client1/login&response_type=code&state=c9mPvB
 */
@SpringBootApplication
@EnableOAuth2Sso  //开启
public class SsoClient1Application {
    public static void main(String[] args) {
        SpringApplication.run(SsoClient1Application.class,args);
    }
}
