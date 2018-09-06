package com.liupeiiqng.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Spring的AOP技术主要有4个核心概念：
 *
 * Pointcut: 切点，用于定义哪个方法会被拦截，例如 execution(* cn.springcamp.springaop.service.*.*(..))
 *
 * Advice: 拦截到方法后要执行的动作
 *
 * Aspect: 切面，把Pointcut和Advice组合在一起形成一个切面
 *
 * Join Point: 在执行时Pointcut的一个实例
 *
 * Weaver: 实现AOP的框架，例如 AspectJ 或 Spring AOP
 *
 * 使用自定义aop 验证接口是否有token
 * @author liupeiqing
 * @data 2018/8/9 20:36
 */
@SpringBootApplication
public class MicroserviceAop extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MicroserviceAop.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAop.class,args);
    }

}
