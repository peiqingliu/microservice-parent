package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author liupeiqing
 * @data 2018/8/16 16:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


}
