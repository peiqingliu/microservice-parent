package com.liupeiiqng.spring.cloud.controller;

import com.liupeiiqng.spring.cloud.annotation.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/8/10 11:58
 */
@RestController
public class AopPermissionController {

    @Permission
    @GetMapping(value = "/login/{id}")
    public String login(@PathVariable("id")Long id){
        String str = "";
        if (id == 1){
             str = "登录成功!";
             return str;
        }
        return "登录失败!";
    }
}
