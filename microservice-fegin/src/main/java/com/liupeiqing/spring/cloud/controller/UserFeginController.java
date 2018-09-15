package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.entity.User;
import com.liupeiqing.spring.cloud.feginclient.SystemBeanFeignClient;
import com.liupeiqing.spring.cloud.feginclient.UserFeginClient;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeginController {

    @Autowired
    private UserFeginClient userFeginClient;

    @Autowired
    private SystemBeanFeignClient systemBeanFeignClient;

    @GetMapping("/feign/{id}")
    public User findUserById(@PathVariable("id") Long id){
        User user = this.userFeginClient.findUserById(id);
        return user;
    }


    @GetMapping("/findUserByusername/{username}")
    public R<AuthUser> findUserByusername(@PathVariable("username") String username){
        R<AuthUser> user = this.systemBeanFeignClient.findUserByusername(username);
        return user;
    }
}
