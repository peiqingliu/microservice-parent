package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.JwtUser;
import com.liupeiqing.spring.cloud.domain.Role;
import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/16 16:34
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(s);
        String[] str = user.getRole().split(",");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在");
        }
        for (String r : str){
            authorities.add(new SimpleGrantedAuthority(r));
        }
        return new JwtUser(user.getId(),user.getUsername(),user.getPassword(),authorities);
    }
}
