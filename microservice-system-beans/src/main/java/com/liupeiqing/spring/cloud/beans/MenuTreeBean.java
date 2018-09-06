package com.liupeiqing.spring.cloud.beans;


import com.liupeiqing.spring.cloud.tree.MenuTree;

import java.io.Serializable;
import java.util.List;

/**
 * 用于redis缓存的必须实现Serializable 接口
 * 
 * @author liuweijw
 */
public class MenuTreeBean implements Serializable {

	private static final long	serialVersionUID	= 2707121320504244801L;

	private List<MenuTree>		menuList;

	private String[]			permissions;

	public List<MenuTree> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuTree> menuList) {
		this.menuList = menuList;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

}
