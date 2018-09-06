package com.liupeiqing.spring.cloud.service;


import com.liupeiqing.spring.cloud.tree.DeptTree;

import java.util.List;

public interface DeptService {

	/**
	 * 得到部门树形列表数据
	 */
	List<DeptTree> getDeptTreeList();

}
