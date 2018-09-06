package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.beans.UserBean;
import com.liupeiqing.spring.cloud.beans.UserForm;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.jpa.JPAFactoryImpl;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import com.liupeiqing.spring.cloud.repository.UserRoleRepository;
import com.liupeiqing.spring.cloud.service.PermissionService;
import com.liupeiqing.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl extends JPAFactoryImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserRoleRepository userRoleRepository;


	@Override
	public AuthUser findUserByUsername(String username) {
		return null;
	}

	@Override
	public User findUserByUsername(String username, boolean isLoadRole) {
		return null;
	}

	@Override
	public AuthUser findUserByMobile(String mobile) {
		return null;
	}

	@Override
	public void saveImageCode(String randomStr, String text) {

	}

	@Override
	public UserBean findUserInfo(AuthUser user) {
		return null;
	}

	@Override
	public AuthUser findByUserId(String userId) {
		return null;
	}

	@Override
	public PageBean<User> findAll(PageParams pageParams, User user) {
		return null;
	}

	@Override
	public Boolean delByUserId(Integer userId) {
		return null;
	}

	@Override
	public boolean addUserAndRole(User user, Integer roleId) {
		return false;
	}

	@Override
	public boolean updateUserAndRole(UserForm userForm) {
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		return false;
	}
}
