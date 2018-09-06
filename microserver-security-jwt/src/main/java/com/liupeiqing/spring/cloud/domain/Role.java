package com.liupeiqing.spring.cloud.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author liupeiqing
 * @data 2018/8/17 16:13
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    public String getAuthority() {
        return name();
    }

}