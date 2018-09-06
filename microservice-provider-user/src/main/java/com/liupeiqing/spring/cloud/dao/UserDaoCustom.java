package com.liupeiqing.spring.cloud.dao;

import com.liupeiqing.spring.cloud.domain.User;

/**
 * @author liupeiqing
 * @data 2018/7/24 9:05
 */
public interface UserDaoCustom {

    public User findUserByusername(String username);
}
