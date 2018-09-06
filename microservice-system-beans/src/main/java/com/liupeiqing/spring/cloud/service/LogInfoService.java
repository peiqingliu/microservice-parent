package com.liupeiqing.spring.cloud.service;


import com.liupeiqing.spring.cloud.domain.LogInfo;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;

public interface LogInfoService {

	public void saveOrUpdate(LogInfo logInfo);

	/**
	 * 日志列表数据
	 */
	PageBean<LogInfo> findAll(PageParams pageParams, LogInfo logInfo);

	/**
	 * 日志删除
	 */
	boolean delById(Long id);
}
