package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liupeiqing
 * @data 2018/9/4 17:22
 */
@SpringBootApplication
@EnableEurekaClient
public class SystemBeansApplication extends SpringBootServletInitializer {

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(SystemBeansApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SystemBeansApplication.class,args);
    }
}
