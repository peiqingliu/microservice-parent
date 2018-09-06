package com.liupeiqing.spring.cloud.authbeans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/9/5 17:11
 */
@Data
public class AuthRole implements Serializable {

    private static final long	serialVersionUID	= -213874145064828983L;

    /**
     * 角色ID
     */
    private Integer				roleId;

    /**
     * 角色名称
     */
    private String				roleName;

    /**
     * 角色编码，唯一
     */
    private String				roleCode;

    /**
     * 描述
     */
    private String				roleDesc;

    /**
     * 0-正常，1-删除
     */
    private Integer				statu;

    public AuthRole() {

    }
    public AuthRole(String roleCode) {
        this.roleCode = roleCode;
    }
}
