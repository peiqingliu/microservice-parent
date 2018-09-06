package com.liupeiqing.spring.cloud.dao.impl;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.dao.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 实现自定的类
 * @author liupeiqing
 * @data 2018/7/23 17:07
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    @PersistenceContext  //注入的是实体管理器，执行持久化操作的
    private EntityManager entityManager;  //实体管理器


    @Override
    public List<User> getAllUser() {
        List<User> users = entityManager.createNativeQuery("select * from user").getResultList();
        for(User u : users){
            System.out.print(u.getUsername()+"**************"+u.getAge());
        }
        return users;
    }
}
