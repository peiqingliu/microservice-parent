package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.domain.LogInfo;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.LogInfoService;
import com.liupeiqing.spring.cloud.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/logs")
@PrePermissions(value = Module.LOG)
public class LogController extends BaseController {

	@Autowired
	private LogInfoService logInfoService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<LogInfo>> list(HttpServletRequest request, LogInfo logInfo,
									 PageParams pageParams) {
		PageBean<LogInfo> pageData = this.logInfoService.findAll(pageParams, logInfo);
		return new R<PageBean<LogInfo>>().data(pageData);
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> del(HttpServletRequest request, @PathVariable Long id) {
		if (null == id || id.intValue() <= 0) return new R<Boolean>().failure("日志id为空");
		boolean isDel = this.logInfoService.delById(id);
		return new R<Boolean>().data(isDel);
	}
}
