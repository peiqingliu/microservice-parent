package com.wuqianqian.move.service;

import com.wuqianqian.move.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author liupeqing
 * @date 2018/11/1 20:04
 */
@Service
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    public User findUserByusername(String username){
        User user = restTemplate.getForObject("http://microservice-provider-user/user/api?username=" + username,User.class);
        return user;
    }

    public User findUserbyId(Long id){
        User user = this.restTemplate.getForObject("http://microservice-provider-user/user/" + id,User.class);
        return user;
    }

    public String helloWorld(String username,int age){
        String str = this.restTemplate.getForObject("http://microservice-provider-user/user/hello?username="+username + "&age=" + age,String.class);
        return str;
    }

    /**
     * 此处调不通？？？
     * @param id
     * @return
     */
    public User findUserById2(Long id){
        User user = this.restTemplate.getForObject("http://10.11.80.24:8087/user/" + id,User.class);
        return user;
    }
}
