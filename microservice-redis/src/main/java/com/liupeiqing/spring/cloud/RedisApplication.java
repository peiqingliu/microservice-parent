package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @EnableCaching 开启缓存
 * @author liupeiqing
 * @data 2018/7/23 13:50
 */
@EnableTransactionManagement  //开启注解
@SpringBootApplication
@EnableCaching
public class RedisApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(RedisApplication.class,args);
    }
}
