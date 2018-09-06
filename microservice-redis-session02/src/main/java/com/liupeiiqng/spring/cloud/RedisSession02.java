package com.liupeiiqng.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liupeiqing
 * @data 2018/8/8 21:09
 */
@EnableTransactionManagement  //开启注解
@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class RedisSession02 {
    public static void main(String[] args) {
        SpringApplication.run(RedisSession02.class,args);
    }
}
