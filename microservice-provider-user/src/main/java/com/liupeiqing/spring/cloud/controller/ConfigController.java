package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.dao.UserDaoCustom;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/7/16 21:13
 */
@RestController
@RequestMapping("/user")
public class ConfigController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscoveryClient discoveryClient;  //发现服务

    @Autowired
    private UserDaoCustom userDaoCustomser;

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long id){
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }


    @GetMapping("/api/{name}/{age}")
    public List<User>  findByName2(@PathVariable("name") String name,@PathVariable("age") int age){
        List<User> findOne = this.userRepository.findByName2(name,age);
        return findOne;
    }

    @GetMapping("/api")
    public User findUserByUserName(@Param("username") String username){
        User user = this.userDaoCustomser.findUserByusername(username);
        return user;
    }

    @GetMapping("/api/{id}")
    public String getString(@PathVariable Integer id){
        String string = "hello world!";
        return string;
    }

    @GetMapping("/hello")
    public String ReturnBy(@Param("username") String username,@Param("age") int age){
        String str = "hello" + username + "/" + age;
        return str;
    }

    /**
     * 本地服务实例的信息
     * @return
     */
//    @GetMapping("/instance-info")
//    public ServiceInstance showInfo() {
//        ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
//        return localServiceInstance;
//    }

}
