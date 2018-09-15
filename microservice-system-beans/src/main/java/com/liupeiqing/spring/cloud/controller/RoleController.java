package com.liupeiqing.spring.cloud.controller;


import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.domain.Role;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.PermissionService;
import com.liupeiqing.spring.cloud.service.RoleService;
import com.liupeiqing.spring.cloud.utils.R;
import com.liupeiqing.spring.cloud.utils.StringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色服务
 * 
 * @author liuweijw
 */
@Api(value = "角色信息管理")
@RestController
@RequestMapping("/role")
@PrePermissions(value = Module.ROLE)
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@ApiOperation(value = "查询", notes = "通过部门deptId查询角色列表数据")
	@ApiImplicitParam(name = "deptId", value = "", required = true, dataType = "int", paramType = "path")
	@GetMapping(value = "/listByDeptId/{deptId}")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Role>> getRoleListByDeptId(@PathVariable("deptId") Integer deptId) {
		return new R<List<Role>>().data(roleService.getRoleListByDeptId(deptId));
	}

	@ApiOperation(value = "查询", notes = "查询角色列表数据")
	@GetMapping(value = "/listAll")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Role>> getRoleList() {
		return new R<List<Role>>().data(roleService.getRoleList());
	}

	@ApiOperation(value = "查询", notes = "角色分页数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "Role"),
			@ApiImplicitParam(name = "pageParams", value = "分页pageParams", required = true, dataType = "PageParams") })
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<Role>> list(HttpServletRequest request, Role role, PageParams pageParams) {
		PageBean<Role> pageData = this.roleService.findAll(pageParams, role);
		return new R<PageBean<Role>>().data(pageData);
	}

	@ApiOperation(value = "新增", notes = "角色", produces = "application/json")
	@ApiImplicitParam(name = "role", value = "", required = true, dataType = "Role")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> add(HttpServletRequest request, @RequestBody Role role) {
		if (null == role) return new R<Boolean>().failure("角色信息为空");
		// 检测权限编码是否存在
		Role exRole = roleService.findRoleByCode(role.getRoleCode());
		if (null != exRole) return new R<Boolean>().failure("权限编码已经存在！").data(false);

		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setStatu(0);
		Role updateObj = roleService.saveOrUpdate(role);
		return new R<Boolean>().data(null != updateObj);
	}

	@ApiOperation(value = "修改", notes = "角色", produces = "application/json")
	@ApiImplicitParam(name = "role", value = "", required = true, dataType = "Dict")
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> upd(HttpServletRequest request, @RequestBody Role role) {
		if (null == role || null == role.getRoleId() || role.getRoleId() <= 0)
			return new R<Boolean>().failure("角色信息为空");
		role.setUpdateTime(new Date());
		Role updateObj = roleService.saveOrUpdate(role);
		return new R<Boolean>().data(null != updateObj);
	}

	@ApiOperation(value = "删除", notes = "角色")
	@ApiImplicitParam(name = "roleId", value = "", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value = "/del/{roleId}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> del(HttpServletRequest request, @PathVariable Integer roleId) {
		if (null == roleId || roleId.intValue() <= 0)
			return new R<Boolean>().failure("角色roleId为空");
		boolean isDel = roleService.delById(roleId);
		return new R<Boolean>().data(isDel);
	}

	@RequestMapping(value = "/updRoleMenuPermission", method = RequestMethod.POST, consumes = "application/json")
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> updRoleMenuPermission(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		String roleCode = params.get("roleCode") + "";
		if (StringHelper.isBlank(roleCode)) return new R<Boolean>().failure("权限编码不能为空");
		String permissionstr = params.get("permissions") + "";
		String[] permissions = permissionstr.split(",");
		if (null == permissions || permissions.length <= 0) permissions = new String[] {};
		boolean isDel = permissionService.updateRoleMenuPermissions(roleCode, permissions);
		return new R<Boolean>().data(isDel);
	}

}
