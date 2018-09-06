package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liupeiqing
 * @data 2018/8/23 20:25
 *
 * 配置多数据源  读写分离 主从复制
 */
@SpringBootApplication
@EnableEurekaClient
public class ShardingjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcApplication.class, args);

    }
}
