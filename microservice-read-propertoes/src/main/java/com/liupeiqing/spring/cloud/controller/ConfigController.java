package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.configUtil.MyWebConfig2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/7/16 21:13
 */
@RestController
public class ConfigController {

    @Autowired
    private MyWebConfig2 conf;

    /**
     * 读取核心配置文件   是该种方法
     */
    @Value("${name}")
    private String name;

    @RequestMapping("/test")
    public String  test() {
        System.out.print("name"+name);
        return "Name:"+conf.getName()+"---"+"Age:"+conf.getAge();
    }
}
