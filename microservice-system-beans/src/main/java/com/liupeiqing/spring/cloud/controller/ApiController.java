//package com.liupeiqing.spring.cloud.controller;
//
//import com.liupeiqing.spring.cloud.annotation.PrePermissions;
//import com.liupeiqing.spring.cloud.authbeans.AuthMenu;
//import com.liupeiqing.spring.cloud.authbeans.AuthPermission;
//import com.liupeiqing.spring.cloud.authbeans.AuthUser;
//import com.liupeiqing.spring.cloud.beans.UserBean;
//import com.liupeiqing.spring.cloud.domain.Dict;
//import com.liupeiqing.spring.cloud.permission.Module;
//import com.liupeiqing.spring.cloud.service.DictService;
//import com.liupeiqing.spring.cloud.service.MenuService;
//import com.liupeiqing.spring.cloud.service.PermissionService;
//import com.liupeiqing.spring.cloud.service.UserService;
//import com.liupeiqing.spring.cloud.utils.R;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * 无须经过网关权限的接口
// *
// * @author liupeiqing
// */
//@RestController
//@RequestMapping("/api")
//@PrePermissions(value = Module.API, required = false)
//public class ApiController extends BaseController {
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private MenuService menuService;
//
//	@Autowired
//	private PermissionService permissionService;
//
//	@Autowired
//	private DictService dictService;
//
//	@Autowired
//	private JwtConfiguration	jwtConfiguration;
//
//	/**
//	 * 获取当前用户信息（角色、权限）
//	 */
//	@GetMapping("/info")
//	public R<UserBean> user(AuthUser user) {
//		return new R<UserBean>().data(userService.findUserInfo(user));
//	}
//
//	/**
//	 * 通过用户名查询用户及其角色信息
//	 */
//	@GetMapping("/findUserByUsername/{username}")
//	public AuthUser findUserByUsername(@PathVariable String username) {
//		return userService.findUserByUsername(username);
//	}
//
//	/**
//	 * 通过手机号码查询用户及其角色信息
//	 */
//	@GetMapping("/findUserByMobile/{mobile}")
//	public AuthUser findUserByMobile(@PathVariable("mobile") String mobile) {
//		return userService.findUserByMobile(mobile);
//	}
//
//	/**
//	 * 返回当前用户树形菜单集合
//	 *
//	 * @return 树形菜单
//	 */
//	@GetMapping("/userTree")
//	public R<List<Integer>> userTree(HttpServletRequest request) {
//		String roleCode = JwtUtil.getRole(request, jwtConfiguration.getJwtkey()).get(0);
//		Set<AuthMenu> menus = menuService.findMenuByRole(roleCode);
//
//		List<Integer> menuList = new ArrayList<>();
//		if (null == menus || menus.size() == 0) return new R<List<Integer>>().data(menuList);
//
//		menus.stream().forEach(menu -> {
//			menuList.add(menu.getMenuId());
//		});
//
//		return new R<List<Integer>>().data(menuList);
//	}
//
//	/**
//	 * 通过用户名查询用户菜单
//	 */
//	@GetMapping("/findMenuByRole/{roleCode}")
//	public Set<AuthPermission> findMenuByRole(@PathVariable String roleCode) {
//		Set<AuthPermission> permissions = new HashSet<AuthPermission>();
//		Set<AuthMenu> menus = menuService.findMenuByRole(roleCode);
//
//		if (null == menus || menus.size() == 0) return permissions;
//
//		menus.stream().forEach(r -> {
//			permissions.add(new AuthPermission(r.getUrl()));
//		});
//		return permissions;
//	}
//
//	/**
//	 * 通过角色获取菜单相关权限列表
//	 */
//	@GetMapping(value = "/findMenuPermissions/{roleCode}")
//	public Set<String> findMenuPermissions(@PathVariable("roleCode") String roleCode) {
//
//		return permissionService.findMenuPermissions(roleCode);
//	}
//
//	/**
//	 * 通过type获取字典数据
//	 */
//	@GetMapping("/dictType/{type}")
//	public R<List<Dict>> findDictByType(@PathVariable String type) {
//		return new R<List<Dict>>().data(dictService.getDictList(type));
//	}
//}
