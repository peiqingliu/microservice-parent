package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.beans.UserForm;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.UserService;
import com.liupeiqing.spring.cloud.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户管理服务
 * 
 * @author liuweijw
 */
@RestController
@Api(value = "用户信息")
@RequestMapping("/user")
@PrePermissions(value = Module.USER)
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 查询用户列表数据
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<User>> list(HttpServletRequest request, User user, PageParams pageParams) {
		PageBean<User> pageData = this.userService.findAll(pageParams, user);
		return new R<PageBean<User>>().data(pageData);
	}

	/**
	 * 通过userId删除数据
	 */
	@ApiOperation(value = "删除用户", notes = "根据userId删除用户")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value = "/del/{userId}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> list(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		return new R<Boolean>().data(this.userService.delByUserId(userId));
	}

	/**
	 * 通过userId 查询信息
	 */
	@ApiOperation(value = "查询用户信息", notes = "根据userId查询用户信息")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value = "/find/{userId}", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<AuthUser> findByUserId(HttpServletRequest request,
									@PathVariable("userId") Integer userId) {
		AuthUser authUser = this.userService.findByUserId(String.valueOf(userId));
		if (null == authUser) return new R<AuthUser>().failure();
		return new R<AuthUser>().success().data(authUser);
	}

	/**
	 * 通过username 查询用户信息
	 * @param request
	 * @param username
	 * @return
	 */
	//@ApiOperation(value = "查询用户信息", notes = "根据用户username查询用户信息")
	//@ApiImplicitParam(name = "username",value = "用户username",required = true,dataType = "String",paramType = "path")
	@GetMapping("/findUserByName/{username}")
	//@PrePermissions(value = Functional.VIEW)
	public R<AuthUser> findUserByusername(HttpServletRequest request,@PathVariable("username") String username){
		AuthUser authUser = this.userService.findUserByUsername(username);
		if (null == authUser) return new R<AuthUser>().failure();
		return new R<AuthUser>().success().data(authUser);
	}

	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> addUser(HttpServletRequest request, @RequestBody UserForm userForm) {
		if (null == userForm.getRoleId()) return new R<Boolean>().failure("请选择角色");
		User user = new User();
		user.setCreateTime(new Date());
		user.setStatu(0);
		user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword().trim()));
		user.setUpdateTime(new Date());
		user.setUsername(userForm.getUsername());
		boolean r = this.userService.addUserAndRole(user, userForm.getRoleId());
		return new R<Boolean>().data(r);
	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> updateUser(HttpServletRequest request, @RequestBody UserForm userForm) {
		if (null == userForm.getUserId()) return new R<Boolean>().failure("用户不存在");
		if (null == userForm.getRoleId()) return new R<Boolean>().failure("请选择角色");

		boolean r = this.userService.updateUserAndRole(userForm);
		return new R<Boolean>().data(r);
	}

	/**
	 * 修改用户密码
	 */
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> modifyUser(HttpServletRequest request, @RequestBody UserForm userForm) {
		if (null == userForm.getUsername()) return new R<Boolean>().failure("用户名不存在");
		if (null == userForm.getPassword()) return new R<Boolean>().failure("请输入旧密码");
		if (null == userForm.getNewpassword()) return new R<Boolean>().failure("请输入新密码");

		User user = this.userService.findUserByUsername(userForm.getUsername().trim(), false);
		if (null == user) return new R<Boolean>().failure("用户名不存在");

		if (!new BCryptPasswordEncoder().matches(userForm.getPassword().trim(), user.getPassword()))
			return new R<Boolean>().failure("旧密码输入错误！");

		user.setPassword(new BCryptPasswordEncoder().encode(userForm.getNewpassword().trim()));

		boolean r = this.userService.updateUser(user);

		return new R<Boolean>().data(r);
	}

}
