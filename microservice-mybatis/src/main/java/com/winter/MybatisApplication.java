package com.winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liupeqing
 * @date 2018/12/17 15:40
 */
@SpringBootApplication
//@MapperScan("com.winter.mapper")  //MapperScan这个进行映射  如果不用这个，在mapper上面加@Mapper
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class,args);
    }
}
