package com.liupeiiqng.spring.cloud.controller;

import com.liupeiiqng.spring.cloud.domain.User;
import com.liupeiiqng.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author liupeiqing
 * @data 2018/8/8 17:13
 */
@RestController
public class RedisSessionController {



    @Autowired
    private UserService userService;

    @GetMapping("/login/{id}")
    public String login(HttpServletRequest request, @PathVariable("id") Long id){
        User user = userService.findByUserId(id);
        request.getSession().setAttribute("id",user);
        return "登录成功";
    }

    @GetMapping("/getSession")
    public Object getSession(HttpServletRequest request){
        Object obj = request.getSession().getId();
        return obj + "我是02";
    }
    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable ("id") Long id ){
        User user =  userService.findByUserId(id);
        return user;
    }
}
