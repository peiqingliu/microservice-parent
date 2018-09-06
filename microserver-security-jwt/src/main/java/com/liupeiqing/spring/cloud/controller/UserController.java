package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/8/17 14:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Integer id){
        User user = this.userRepository.findOne(id);
        return user;
    }

}
