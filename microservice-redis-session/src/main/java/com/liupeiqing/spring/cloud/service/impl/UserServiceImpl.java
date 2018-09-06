package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/8/8 20:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "user_info_",key = " 'user_'+ #userId")
    public User findByUserId(Long userId) {
       User user = this.userRepository.findOne(userId);
        return user;
    }
}
