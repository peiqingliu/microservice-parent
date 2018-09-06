package com.liupeiqing.spring.cloud.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/7/18 9:08
 */
@Component
public class User implements Serializable {
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
