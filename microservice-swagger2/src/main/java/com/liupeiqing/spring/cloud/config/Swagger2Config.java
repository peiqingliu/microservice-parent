package com.liupeiqing.spring.cloud.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liupeiqing
 * @data 2018/8/20 11:11
 * swagger2的配置类
 */
@Configuration
@EnableSwagger2  //开启swagger2
public class Swagger2Config {

    /**
     * 通过 createRestApi函数来构建一个DocketBean
     * 函数名,可以随意命名,喜欢什么命名就什么命名
     */
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                //调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .apiInfo(apiInfo())
                //控制暴露出去的路径下的实例
                //				//如果某个接口不想暴露,可以使用以下注解
                //				//@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.liupeiqing.spring.cloud.controller"))
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Spring Boot 使用 Swagger2 构建RESTful API")
                .description("用于测试Restful接口")
                .contact("liupeiqing")
                .version("1.0")
                .build();
    }

}
