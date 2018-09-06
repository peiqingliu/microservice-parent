package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/7/23 17:49
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable ("id") Long id ){
        User user =  userService.findByUserId(id);
        return user;
    }

    @GetMapping(value = "/findAllUser")
    public List<User> findAllUser(){
        List<User> users = userService.findAllUser();
        return users;
    }
}
