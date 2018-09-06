package com.liupeiqing.spring.cloud.dao;

import com.liupeiqing.spring.cloud.domain.User;

import java.util.List;

/**
 * 自定义接口
 * Notice： 这里的Custom后缀是约定的，不能随意修改。
 * @author liupeiqing
 * @data 2018/7/23 17:31
 */
public interface UserRepositoryCustom {


    //自定义接口方法
    public List<User> getAllUser();
}
