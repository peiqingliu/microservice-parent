package com.liupeiqing.spring.cloud.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liuweijw
 */
@Entity
@Table(name = RoleDept.TABLE_NAME)
public class RoleDept implements Serializable {

	private static final long	serialVersionUID	= -1028920072723837099L;

	public static final String	TABLE_NAME			= "t_sys_role_dept";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	@Column(name = "role_id")
	private Integer				roleId;

	@Column(name = "dept_id")
	private Integer				deptId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
