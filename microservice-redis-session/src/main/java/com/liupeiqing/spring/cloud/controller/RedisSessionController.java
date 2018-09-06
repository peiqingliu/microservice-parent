package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中  一般session是放到内存中的，但是次例使用redis管理session，因此是放在redis中的
        //服务器创建session之后，会把session的id，cookie的形式，返回给客户端，客户端，每次访问的时候，都会携带这个session的id,服务器发现客户端
        //携带session的id过来了，就会和内存中的session对比
        session.setAttribute("id",user);
        return "登录成功";
    }

    @GetMapping("/getSession")
    public Object getSession(HttpServletRequest request){
        Object obj = request.getSession().getId();
        return obj + "我是01";
    }
    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable ("id") Long id ){
        User user =  userService.findByUserId(id);
        return user;
    }
}
