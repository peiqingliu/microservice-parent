package com.liupeiiqng.spring.cloud.service;


import com.liupeiiqng.spring.cloud.domain.User;

/**
 * @author liupeiqing
 * @data 2018/8/8 20:18
 */
public interface UserService {

    public User findByUserId(Long userId);
}
