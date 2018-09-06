package com.liupeiqing.spring.cloud.moudle;

import lombok.Data;

/**
 * @author liupeiqing
 * @data 2018/8/16 17:12
 */
@Data
public class LoginUser {
    private String username;
    private String password;
    private Integer rememberMe;

}
