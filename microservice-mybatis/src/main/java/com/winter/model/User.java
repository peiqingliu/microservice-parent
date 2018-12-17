package com.winter.model;

import lombok.Data;

/**
 * @author liupeqing
 * @date 2018/12/17 15:41
 */
@Data
public class User {

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
