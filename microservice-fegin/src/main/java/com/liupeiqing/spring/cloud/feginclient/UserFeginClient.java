package com.liupeiqing.spring.cloud.feginclient;

import com.liupeiqing.spring.cloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("microservice-provider-user")
public interface UserFeginClient {

    @RequestMapping("/{id}")
    public User findUserById(@RequestParam("id") Long id);
}
