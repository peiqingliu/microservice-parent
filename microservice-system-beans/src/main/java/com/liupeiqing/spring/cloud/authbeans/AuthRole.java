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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}
