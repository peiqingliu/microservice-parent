package com.liupeiiqng.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author liupeqing
 * @date 2018/10/17 16:56
 */
@SpringBootApplication
public class NettyApplication extends SpringBootServletInitializer {

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(NettyApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class,args);
    }
}
