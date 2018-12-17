package com.winter.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.winter.mapper.UserMapper;
import com.winter.model.User;
import com.winter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/12/17 15:48
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addUser(User user) {
        return this.userMapper.insertSelective(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //分页插件
        PageHelper.startPage(pageNum,pageSize);
        return this.userMapper.selectAllUser();
    }
}
