package com.liupeiiqng.spring.cloud.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author liupeiqing
 * @data 2018/8/9 21:00
 */
@Retention(RetentionPolicy.RUNTIME) //什么时候运行  注解的生命周期
@Target({ElementType.TYPE,ElementType.METHOD})  //注解用于什么地方  作用域
@Documented
public @interface Permission {

    String param() default "";
}
