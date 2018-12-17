package com.liupeiqing.springcloud.controller;

import com.liupeiqing.springcloud.mapper.UserMapper;
import com.liupeiqing.springcloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeqing
 * @date 2018/12/17 16:40
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/user")
    public User findUserByPhone(@RequestParam("phone") String phone){
    User user = this.userMapper.findUserByPhone(phone);
    return user;
    }

}
