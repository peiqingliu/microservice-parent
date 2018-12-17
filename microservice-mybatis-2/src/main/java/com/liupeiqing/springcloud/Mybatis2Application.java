package com.liupeiqing.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liupeqing
 * @date 2018/12/17 16:39
 */
@SpringBootApplication
@EnableEurekaClient
public class Mybatis2Application {

    public static void main(String[] args) {
        SpringApplication.run(Mybatis2Application.class,args);
    }
}
