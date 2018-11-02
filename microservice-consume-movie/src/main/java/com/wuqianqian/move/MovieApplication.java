package com.wuqianqian.move;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author liupeqing
 * @date 2018/11/1 20:01
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MovieApplication extends SpringBootServletInitializer {

    /**
     * 实例化 RestTemplate restTemplate客户端通过
     * @LoadBalanced注解开启均衡负载能力.
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class,args);
    }
}
