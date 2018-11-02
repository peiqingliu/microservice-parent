package com.wuqianqian.move.controller;

import com.wuqianqian.move.domain.User;
import com.wuqianqian.move.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeqing
 * @date 2018/11/1 20:13
 */
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/user/api")
    public User findUserByusername(@Param("username") String username){
        User user = this.movieService.findUserByusername(username);
        return user;
    }

    @GetMapping("/{id}")
    public User findUserByid(@PathVariable(value = "id") Long id){
        User user = this.movieService.findUserbyId(id);
        return user;
    }

    @GetMapping("/hello")
    public String getHello(@Param("username") String username,@Param("age") int age){
        String str = this.movieService.helloWorld(username,age);
        return str;
    }

    @GetMapping("/test/{id}")
    public User findUserByid2(@PathVariable("id") Long id){
        User user = this.movieService.findUserById2(id);
        return user;
    }

}
