package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liupeiqing
 * @data 2018/8/20 14:32
 * 提供soap协议的webservice接口，还要提供Restful API接口
 */
@SpringBootApplication
@EnableEurekaClient
public class WebServiceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class,args);
    }
}
