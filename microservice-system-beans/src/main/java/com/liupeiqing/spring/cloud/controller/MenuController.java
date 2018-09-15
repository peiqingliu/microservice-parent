package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.beans.MenuTreeBean;
import com.liupeiqing.spring.cloud.domain.Menu;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.MenuService;
import com.liupeiqing.spring.cloud.service.PermissionService;
import com.liupeiqing.spring.cloud.tree.MenuTree;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单服务
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping("/menu")
@PrePermissions(value = Module.MENU)
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private PermissionService permissionService;

	// 获取所有菜单列表 以及功能权限
	@GetMapping(value = "/menuTreeList/{roleCode}")
	@PrePermissions(value = Functional.VIEW)
	public R<MenuTreeBean> menuTreeList(@PathVariable("roleCode") String roleCode) {
		MenuTreeBean menuTreeBean = new MenuTreeBean();

		List<MenuTree> menuTree = menuService.findAllMenuTree();
		menuTreeBean.setMenuList(menuTree);

		Set<String> permissions = new HashSet<String>();
		permissions.addAll(permissionService.findMenuPermissions(roleCode));

		menuTreeBean.setPermissions(permissions.toArray(new String[permissions.size()]));

		return new R<MenuTreeBean>().data(menuTreeBean);
	}

	// 获取所有菜单列表
	@GetMapping(value = "/menuTreeAllList")
	@PrePermissions(value = Functional.VIEW)
	public R<MenuTreeBean> menuTreeList() {
		MenuTreeBean menuTreeBean = new MenuTreeBean();
		List<MenuTree> menuTree = menuService.findAllMenuTreeList();
		menuTreeBean.setMenuList(menuTree);
		return new R<MenuTreeBean>().data(menuTreeBean);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> add(HttpServletRequest request, @RequestBody Menu menu) {
		if (null == menu) return new R<Boolean>().failure("菜单信息为空");
		if (null == menu.getPid()) return new R<Boolean>().failure("请选择菜单再点击进行新增");

		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		menu.setStatu(0);
		Menu menuObj = menuService.saveOrUpdate(menu);
		return new R<Boolean>().data(null != menuObj);
	}

	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> upd(HttpServletRequest request, @RequestBody Menu menu) {
		if (null == menu) return new R<Boolean>().failure("菜单信息为空");
		if (null == menu.getPid() || null == menu.getMenuId())
			return new R<Boolean>().failure("请选择菜单再点击进行修改");

		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		menu.setStatu(0);
		Menu menuObj = menuService.saveOrUpdate(menu);
		return new R<Boolean>().data(null != menuObj);
	}

	@RequestMapping(value = "/del/{menuId}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> del(HttpServletRequest request, @PathVariable Integer menuId) {
		if (null == menuId || menuId.intValue() <= 0)
			return new R<Boolean>().failure("菜单menuId为空");
		boolean isDel = menuService.delById(menuId);
		return new R<Boolean>().data(isDel);
	}
}
