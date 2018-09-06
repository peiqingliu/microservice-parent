package com.liupeiqing.spring.cloud.dao.impl;

import com.liupeiqing.spring.cloud.dao.UserDaoCustom;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/7/24 9:06
 */
@Service
public class UserRepositoryImpl implements UserDaoCustom {

    @Autowired
    private UserRepository userRepository;



    @Override
    public User findUserByusername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
