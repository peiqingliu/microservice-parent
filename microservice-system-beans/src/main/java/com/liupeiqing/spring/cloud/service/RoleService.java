package com.liupeiqing.spring.cloud.service;


import com.liupeiqing.spring.cloud.domain.Role;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;

import java.util.List;

public interface RoleService {

	List<Role> getRoleListByDeptId(Integer deptId);

	List<Role> getRoleList();

	PageBean<Role> findAll(PageParams pageParams, Role role);

	Role saveOrUpdate(Role role);

	boolean delById(Integer roleId);

	Role findRoleByCode(String roleCode);

}
