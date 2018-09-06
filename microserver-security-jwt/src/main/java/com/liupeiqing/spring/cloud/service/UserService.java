package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author liupeiqing
 * @data 2018/8/16 16:42
 */
public interface UserService {

    public User findUserByUsername(String username);

    //Collection<GrantedAuthority> loadUserAuthorities(String username);

}
