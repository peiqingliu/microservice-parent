package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.User;

/**
 * @author liupeiqing
 * @data 2018/8/8 20:18
 */
public interface UserService {

    public User findByUserId(Long userId);
}
