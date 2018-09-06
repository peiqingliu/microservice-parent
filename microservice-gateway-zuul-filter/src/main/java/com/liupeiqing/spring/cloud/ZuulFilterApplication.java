package com.liupeiqing.spring.cloud;

import com.liupeiqing.spring.cloud.filter.PreRequestLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author liupeiqing
 * @data 2018/7/17 16:22
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulFilterApplication {
    public static void main(String[] args){
        SpringApplication.run(ZuulFilterApplication.class,args);
    }


    /**
     * @Bean 用在方法上，告诉Spring容器，你可以从下面这个方法中拿到一个Bean
     * @return
     */
    @Bean
    public PreRequestLogFilter preRequestLogFilter() {
        return new PreRequestLogFilter();
    }
}
