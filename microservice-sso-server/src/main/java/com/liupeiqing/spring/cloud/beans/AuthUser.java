package com.liupeiqing.spring.cloud.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/9/4 16:42
 */
@Data
public class AuthUser implements Serializable {
    private static final long	serialVersionUID	= 5350461830095965990L;

    /**
     * 用户名Id
     */
    private Integer				userId;
    /**
     * 用户名
     */
    private String				username;
    /**
     * 密码
     */
    private String				password;
    /**
     * 0-正常，1-删除
     */
    private Integer				statu				= 0;
    /**
     * 头像
     */
    private String				picUrl;
    /**
     * 角色列表
     */
    private List<AuthRole> roleList			= new ArrayList<AuthRole>();
}
