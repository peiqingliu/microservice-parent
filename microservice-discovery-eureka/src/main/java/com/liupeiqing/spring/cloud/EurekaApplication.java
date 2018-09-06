package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liupeiqing
 * @data 2018/7/6 14:51
 */
@SpringBootApplication
@EnableEurekaServer  //开启eureka 服务
public class EurekaApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(EurekaApplication.class,args);
    }
}
