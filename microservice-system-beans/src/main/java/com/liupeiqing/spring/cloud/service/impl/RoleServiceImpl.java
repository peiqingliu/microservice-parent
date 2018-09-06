package com.liupeiqing.spring.cloud.service.impl;


import com.liupeiqing.spring.cloud.cache.AdminCacheKey;
import com.liupeiqing.spring.cloud.domain.QRole;
import com.liupeiqing.spring.cloud.domain.QRoleDept;
import com.liupeiqing.spring.cloud.domain.Role;
import com.liupeiqing.spring.cloud.jpa.JPAFactoryImpl;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.repository.RoleRepository;
import com.liupeiqing.spring.cloud.service.RoleService;
import com.liupeiqing.spring.cloud.utils.PageUtils;
import com.liupeiqing.spring.cloud.utils.StringHelper;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class RoleServiceImpl extends JPAFactoryImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_' + #roleCode")
	public Role findRoleByCode(String roleCode) {
		if (StringUtils.isEmpty(roleCode)) return null;

		QRole qRole = QRole.role;
		return this.queryFactory.selectFrom(qRole).where(qRole.roleCode.eq(roleCode.trim()))
				.fetchOne();
	}

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_' + #deptId")
	public List<Role> getRoleListByDeptId(Integer deptId) {
		if (null == deptId || deptId <= 0) return null;

		// load role
		QRoleDept qRoleDept = QRoleDept.roleDept;
		QRole qRole = QRole.role;
		List<Role> rList = this.queryFactory.select(qRole).from(qRoleDept, qRole).where(
				qRoleDept.deptId.eq(deptId)).where(qRoleDept.roleId.eq(qRole.roleId)).fetch();

		return rList;
	}

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_list'")
	public List<Role> getRoleList() {
		return roleRepository.findAll();
	}

	@Override
	public PageBean<Role> findAll(PageParams pageParams, Role role) {
		QRole qRole = QRole.role;
		// 用户名查询条件
		Predicate qNamePredicate = null;
		Predicate qCodePredicate = null;
		if (null != role) {
			if (StringHelper.isNotBlank(role.getRoleName())) {
				qNamePredicate = qRole.roleName.like("%" + role.getRoleName().trim() + "%");
			}
			if (StringHelper.isNotBlank(role.getRoleCode())) {
				qCodePredicate = qRole.roleCode.like("%" + role.getRoleCode().trim() + "%");
			}
		}

		Predicate predicate = qRole.roleId.goe(0).and(qCodePredicate).and(qNamePredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "roleId"));
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<Role> pageList = roleRepository.findAll(predicate, pageRequest);
		return PageUtils.of(pageList);
	}


	@Override
	@CacheEvict(value = { AdminCacheKey.ROLE_INFO }, allEntries = true)
	@Transactional
	public Role saveOrUpdate(Role role) {
		if (null == role) return null;

		Role dbRole = this.roleRepository.saveAndFlush(role);

		return dbRole;
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.ROLE_INFO }, allEntries = true)
	@Transactional
	public boolean delById(Integer roleId) {
		if (null == roleId || roleId <= 0) return Boolean.FALSE;

		QRole qRole = QRole.role;
		long num = this.queryFactory.update(qRole).set(qRole.statu, 1) // 0 正常 1删除
				.where(qRole.roleId.eq(roleId.intValue())).execute();

		return num > 0;
	}

}
