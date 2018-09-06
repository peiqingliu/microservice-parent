package com.liupeiqing.spring.cloud.feginClient;

import com.liupeiqing.spring.cloud.User.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/9/4 15:52
 */
@Slf4j
@Service
public class UserFallback implements UserFeignClient {
    @Override
    public User findUserByUserName(String username) {
        log.error("调用{}异常:{}", "findUserByUsername", username);
        User user = new User();
        user.setId(-1L);
        user.setUsername("default username");
        user.setAge(0);
        return user;
    }
}
