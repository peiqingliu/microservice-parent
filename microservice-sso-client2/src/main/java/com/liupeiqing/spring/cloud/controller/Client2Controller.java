package com.liupeiqing.spring.cloud.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Client2Controller {
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

}
