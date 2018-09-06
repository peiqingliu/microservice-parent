package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.User.User;
import com.liupeiqing.spring.cloud.beans.AuthUser;
import com.liupeiqing.spring.cloud.feginClient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author liupeiqing
 * @data 2018/9/4 16:59
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userFeignClient.findUserByUserName(username);
        AuthUser authUser = new AuthUser();
        authUser.setUsername(user.getUsername());
        UserDetailsImpl userDetails = new UserDetailsImpl(authUser);
        return userDetails;
    }
}
