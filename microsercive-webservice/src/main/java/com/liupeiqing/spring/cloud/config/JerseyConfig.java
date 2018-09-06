package com.liupeiqing.spring.cloud.config;


import com.liupeiqing.spring.cloud.restservice.RestUserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;


/**
 * @author liupeiqing
 * @data 2018/8/21 10:16
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(RestUserService.class);
    }

}
