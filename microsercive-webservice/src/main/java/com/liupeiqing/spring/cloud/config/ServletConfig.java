package com.liupeiqing.spring.cloud.config;

import com.liupeiqing.spring.cloud.webservice.UserService;
import com.liupeiqing.spring.cloud.webservice.impl.UserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author liupeiqing
 * @data 2018/8/20 16:18
 * servlet配置 及webService服务发布
 */
@Configuration
public class ServletConfig {

    /**
     * webservice 接口处理
     * 这里是定义处理请求的servlet，以及映射路径
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherServlet(){
        return new ServletRegistrationBean(new CXFServlet(),"/ws/*");
    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public UserService testService() {
        return (UserService) new UserServiceImpl();
    }

    //使用Endpoint(端点)类发布webservice
    @Bean
    public Endpoint endpointUser(){
        EndpointImpl endpoint =new EndpointImpl(springBus(),testService());
        endpoint.publish("/userWService");
        return endpoint;
    }
}
