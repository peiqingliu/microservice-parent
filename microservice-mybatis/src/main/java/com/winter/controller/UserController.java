package com.winter.controller;

import com.winter.model.User;
import com.winter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author liupeqing
 * @date 2018/12/17 15:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
    @GetMapping("/findUserById")
    public User findUserById(@RequestParam("id") Integer id){
        User user = this.userService.findUserById(id);
        return user;
    }

    @RequestMapping("/add")
    public int addUser(User user){
        return userService.addUser(user);
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/all/{pageNum}/{pageSize}")
    public Object findAllUser(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return this.userService.findAllUser(pageNum,pageSize);
    }
}
