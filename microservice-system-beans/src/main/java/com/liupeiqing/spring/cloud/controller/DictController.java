package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.annotation.PrePermissions;
import com.liupeiqing.spring.cloud.domain.Dict;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.permission.Functional;
import com.liupeiqing.spring.cloud.permission.Module;
import com.liupeiqing.spring.cloud.service.DictService;
import com.liupeiqing.spring.cloud.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 字典管理
 * 
 * @author liuweijw
 */
@Api(value = "字典信息管理")  //协议集描述 	用于controller类上
@RestController
@RequestMapping("/dict")
@PrePermissions(value = Module.DICT)
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;

	@ApiOperation(value = "查询", notes = "字典分页数据")  //协议描述  	用在controller的方法上
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dict", value = "字典实体dict", required = true, dataType = "Dict"),
			@ApiImplicitParam(name = "pageParams", value = "分页pageParams", required = true, dataType = "PageParams") })
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<Dict>> list(HttpServletRequest request, Dict dict, PageParams pageParams) {
		PageBean<Dict> pageData = this.dictService.findAll(pageParams, dict);
		return new R<PageBean<Dict>>().data(pageData);
	}

	@ApiOperation(value = "查询", notes = "根据id查询字典数据")
	@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int", paramType = "path")
	@GetMapping("/{id}")
	@PrePermissions(value = Functional.VIEW)
	public R<Dict> findDictById(@PathVariable Integer id) {
		return new R<Dict>().data(dictService.findById(id));
	}

	@ApiOperation(value = "查询", notes = "根据type查询字典数据")
	@ApiImplicitParam(name = "type", value = "", required = true, dataType = "string", paramType = "path")
	@GetMapping("/type/{type}")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Dict>> findDictByType(@PathVariable String type) {
		return new R<List<Dict>>().data(dictService.getDictList(type));
	}

	@ApiOperation(value = "查询", notes = "查询字典列表数据")
	@GetMapping("/typeList")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Dict>> findDictList() {
		return new R<List<Dict>>().data(dictService.getAllList());
	}

	@ApiOperation(value = "新增", notes = "字典类别", produces = "application/json")
	@ApiImplicitParam(name = "dict", value = "", required = true, dataType = "Dict")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> add(HttpServletRequest request, @RequestBody Dict dict) {
		if (null == dict) return new R<Boolean>().failure("字典信息为空");
		Dict updateDict = dictService.saveOrUpdate(dict);
		return new R<Boolean>().data(null != updateDict);
	}

	@ApiOperation(value = "修改", notes = "字典类别", produces = "application/json")
	@ApiImplicitParam(name = "dict", value = "", required = true, dataType = "Dict")
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> upd(HttpServletRequest request, @RequestBody Dict dict) {
		if (null == dict || null == dict.getId() || dict.getId() <= 0)
			return new R<Boolean>().failure("字典信息为空");
		Dict updateDict = dictService.saveOrUpdate(dict);
		return new R<Boolean>().data(null != updateDict);
	}

	@ApiOperation(value = "删除", notes = "字典类别")
	@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> del(HttpServletRequest request, @PathVariable Integer id) {
		if (null == id || id.intValue() <= 0) return new R<Boolean>().failure("字典id为空");
		boolean isDel = dictService.delById(id);
		return new R<Boolean>().data(isDel);
	}
}
