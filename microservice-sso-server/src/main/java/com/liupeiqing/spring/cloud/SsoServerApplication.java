package com.liupeiqing.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author liupeiqing
 * @data 2018/9/3 20:44
 * 1.若打包成war包,则需要继承 org.springframework.boot.context.web.SpringBootServletInitializer类,覆盖其config(SpringApplicationBuilder)方法
 *
 * 2.打包成war的话,如果打包之后的文件中没有web.xml文件的话自己可以加进去一个最简单的web.xml(只有根节点的定义,而没有子元素)，防止因缺乏web.xml文件而部署失败
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients  //开启fegin
public class SsoServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoServerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class,args);
    }
}
