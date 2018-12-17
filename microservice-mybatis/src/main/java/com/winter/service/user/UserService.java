package com.winter.service.user;

import com.winter.model.User;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/12/17 15:45
 */
public interface UserService {

    User findUserById(Integer id);

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
