package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/9/1 16:31
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api")
    public void use(@RequestParam("name") String name){
        userService.use(name);
    }
}
