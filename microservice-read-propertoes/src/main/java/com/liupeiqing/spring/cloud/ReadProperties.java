package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author liupeiqing
 * @data 2018/7/16 19:25
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.liupeiqing.spring.cloud.filter")
public class ReadProperties {
    public static void main(String[] args){
        SpringApplication.run(ReadProperties.class,args);
    }
}
