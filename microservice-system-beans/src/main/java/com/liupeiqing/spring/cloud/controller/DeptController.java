package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.DeptService;
import com.liupeiqing.spring.cloud.tree.DeptTree;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理服务
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping("/dept")
@PrePermissions(value = Module.DEPT)
public class DeptController extends BaseController {

	@Autowired
	private DeptService deptService;

	@GetMapping(value = "/tree")
	@PrePermissions(value = Functional.VIEW)
	public R<List<DeptTree>> getDeptTreeList() {
		return new R<List<DeptTree>>().data(deptService.getDeptTreeList());
	}

}
