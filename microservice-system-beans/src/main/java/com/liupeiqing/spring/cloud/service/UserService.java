package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.beans.UserBean;
import com.liupeiqing.spring.cloud.beans.UserForm;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;

public interface UserService {

	public AuthUser findUserByUsername(String username);

	public User findUserByUsername(String username, boolean isLoadRole);

	public AuthUser findUserByMobile(String mobile);

	public void saveImageCode(String randomStr, String text);

	public UserBean findUserInfo(AuthUser user);

	public AuthUser findByUserId(String userId);

	public PageBean<User> findAll(PageParams pageParams, User user);

	public Boolean delByUserId(Integer userId);

	public boolean addUserAndRole(User user, Integer roleId);

	public boolean updateUserAndRole(UserForm userForm);

	public boolean updateUser(User user);

}
