package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.beans.AuthRole;
import com.liupeiqing.spring.cloud.beans.AuthUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/9/4 16:35
 */
@Data
public class UserDetailsImpl implements UserDetails {

    private String				username;
    private String				password;
    private Integer				status				= 0;
    private List<AuthRole> roleList	= new ArrayList<AuthRole>();

    public UserDetailsImpl(AuthUser user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status   = user.getStatu();
        this.roleList = user.getRoleList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorList = new ArrayList<GrantedAuthority>();
        for(AuthRole role : roleList){
            authorList.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        return authorList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
