package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liupeiqing
 * @data 2018/8/10 17:11
 */
@SpringBootApplication
@EnableEurekaClient
public class MicroserviceAsync extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAsync.class,args);
    }
}
