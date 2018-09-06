package com.liupeiqing.spring.cloud.beans;


import com.liupeiqing.spring.cloud.domain.User;

import java.io.Serializable;

/**
 * @author liuweijw
 */
public class UserBean implements Serializable {

	private static final long	serialVersionUID	= 4100476652382025202L;

	/**
	 * 用户基本信息
	 */
	private User user;
	/**
	 * 权限标识集合
	 */
	private String[]			permissions;

	/**
	 * 角色集合
	 */
	private String[]			roles;

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
