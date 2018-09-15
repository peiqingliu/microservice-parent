package com.liupeiqing.spring.cloud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/9/4 16:59
 */
@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 对传进来的密码进行加密
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
