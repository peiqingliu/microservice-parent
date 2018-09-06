package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.User;

import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/7/23 16:57
 */

public interface UserService {

    public List<User> findAllUser();

    public User findByUserId(Long userId);

    public boolean delUser(Long userId);
}
