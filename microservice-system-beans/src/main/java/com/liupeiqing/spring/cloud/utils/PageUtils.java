package com.liupeiqing.spring.cloud.utils;


import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具 注意分页是从0开始的
 * 
 * @author liuweijw
 */
public class PageUtils {

	/**
	 * <T> 表示泛型方法  PageBean<T>返回值
	 * @param resultList
	 * @param total
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public static <T> PageBean<T> of(List<T> resultList, Long total, Integer currentPage,
			Integer pageSize) {
		PageBean<T> pageData = new PageBean<T>();
		pageData.setCurrentPage(currentPage + 1);
		pageData.setPageSize(pageSize);
		pageData.setTotal(total);
		List<T> newList = new ArrayList<T>();
		if (null != resultList && resultList.size() > 0) newList.addAll(resultList);
		pageData.setList(newList);
		return pageData;
	}

	public static <T> PageBean<T> of(Page<T> pageList) {
		PageBean<T> pageData = new PageBean<T>();
		pageData.setCurrentPage(pageList.getNumber() + 1);
		pageData.setPageSize(pageList.getSize());
		pageData.setTotal(pageList.getTotalElements());
		List<T> newList = new ArrayList<T>();
		List<T> contentList = pageList.getContent();
		if (null != contentList && contentList.size() > 0) newList.addAll(pageList.getContent());
		pageData.setList(newList);
		return pageData;
	}

	public static PageRequest of(PageParams pageParams, Sort sort) {
		return new PageRequest(pageParams.getCurrentPage() - 1, pageParams.getPageSize(), sort);
	}

	public static PageRequest of(PageParams pageParams) {
		return new PageRequest(pageParams.getCurrentPage() - 1, pageParams.getPageSize());
	}

}
