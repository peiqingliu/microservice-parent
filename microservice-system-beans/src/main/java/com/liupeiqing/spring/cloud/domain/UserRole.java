package com.liupeiqing.spring.cloud.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liuweijw
 */
@Entity
@Table(name = UserRole.TABLE_NAME)
public class UserRole implements Serializable {

	private static final long	serialVersionUID	= 8409879328945905867L;

	public static final String	TABLE_NAME			= "t_sys_user_role";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	@Column(name = "user_id")
	private Integer				userId;

	@Column(name = "role_id")
	private Integer				roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
