package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liupeiqing
 * @data 2018/8/8 17:10
 */
@EnableTransactionManagement  //开启注解
@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class RedisSession extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RedisSession.class,args);
    }
}
