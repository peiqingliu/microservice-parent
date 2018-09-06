package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.redisLock.RedisLock;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author liupeiqing
 * @data 2018/9/1 16:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisLock redisLock;

    @Override
    public void use(String name) {
        String key = "key" + name;

        //随机生成一个与key对应的value
        String requestId = UUID.randomUUID().toString();
        try{
            boolean lock = redisLock.tryLock(key,requestId,1000 * 1000);
            if (!lock){
                System.out.println("locked error");
                return;
            }

            //do something
            System.out.println("locked success" + name);
        }finally {
            redisLock.unlock(key,requestId);
        }
    }
}
