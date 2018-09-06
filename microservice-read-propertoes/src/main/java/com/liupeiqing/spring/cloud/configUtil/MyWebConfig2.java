package com.liupeiqing.spring.cloud.configUtil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/16 20:27
 */
//加上注释@Component，可以直接在其他地方使用@Autowired来创建其实例对象
@Component
@ConfigurationProperties(prefix = "author")
@PropertySource(value = "classpath:config/author.properties")
public class MyWebConfig2 {

    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
