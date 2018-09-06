package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liupeiqing
 * @data 2018/8/13 17:22
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling  //开启任务
public class ScheduleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class,args);
    }
}
