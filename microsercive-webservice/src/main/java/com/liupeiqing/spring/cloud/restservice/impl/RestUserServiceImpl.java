package com.liupeiqing.spring.cloud.restservice.impl;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.restservice.RestUserService;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/8/20 20:00
 */
@Service
public class RestUserServiceImpl implements RestUserService {

    @Override
    public User findUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("刘培情");
        user.setAge(12);
        return user;
    }
}
