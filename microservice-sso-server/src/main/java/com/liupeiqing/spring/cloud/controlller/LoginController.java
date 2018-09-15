package com.liupeiqing.spring.cloud.controlller;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.feginClient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    @Autowired
    //@Qualifier("userFeignClient")
    private UserFeignClient userFeignClient;

    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("/login");
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/fegin/{username}")
    public AuthUser findAuthuserByusername(@PathVariable("username") String username) {
        AuthUser authUser = this.userFeignClient.findUserByusername(username).getData();
        return authUser;
    }
}
