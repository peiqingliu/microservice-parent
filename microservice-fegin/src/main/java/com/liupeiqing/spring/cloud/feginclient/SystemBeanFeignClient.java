package com.liupeiqing.spring.cloud.feginclient;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("microservice-system-beans")
public interface SystemBeanFeignClient {

    @RequestMapping(value = "/findUserByName/{username}",method = RequestMethod.GET)
    public R<AuthUser> findUserByusername(@RequestParam("username") String username);
}
