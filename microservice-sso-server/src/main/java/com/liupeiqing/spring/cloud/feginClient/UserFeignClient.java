package com.liupeiqing.spring.cloud.feginClient;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liupeiqing
 * @data 2018/9/4 15:39
 */

@FeignClient("microservice-system-beans")
public interface UserFeignClient {

    @RequestMapping(value = "/findUserByName/{username}",method = RequestMethod.GET)
    R<AuthUser> findUserByusername(@RequestParam("username") String username);
}

