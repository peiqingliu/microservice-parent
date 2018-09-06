package com.liupeiqing.spring.cloud.feginClient;

import com.liupeiqing.spring.cloud.User.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liupeiqing
 * @data 2018/9/4 15:39
 */
@FeignClient(name = "microservice-provider-user",fallback = UserFallback.class)
public interface UserFeignClient {

    @GetMapping("/api")
    public User findUserByUserName(String username);
}
