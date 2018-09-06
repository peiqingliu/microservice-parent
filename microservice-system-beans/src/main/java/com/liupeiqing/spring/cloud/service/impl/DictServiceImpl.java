package com.liupeiqing.spring.cloud.service.impl;


import com.liupeiqing.spring.cloud.cache.AdminCacheKey;
import com.liupeiqing.spring.cloud.domain.Dict;
import com.liupeiqing.spring.cloud.domain.QDict;
import com.liupeiqing.spring.cloud.jpa.JPAFactoryImpl;
import com.liupeiqing.spring.cloud.page.PageBean;
import com.liupeiqing.spring.cloud.page.PageParams;
import com.liupeiqing.spring.cloud.repository.DictRepository;
import com.liupeiqing.spring.cloud.service.DictService;
import com.liupeiqing.spring.cloud.utils.PageUtils;
import com.liupeiqing.spring.cloud.utils.StringHelper;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = AdminCacheKey.DICT_INFO)
@Component
public class DictServiceImpl extends JPAFactoryImpl implements DictService {

	@Autowired
	private DictRepository dictRepository;

	// @Cacheable(value=AdminCacheKey.DICT_INFO_LIST, key="#pageParams.pageNo_#pageParams.pageNum", condition="#user.id%2==0")
	@Override
	public PageBean<Dict> findAll(PageParams pageParams, Dict dict) {
		QDict qDict = QDict.dict;
		// 用户名查询条件
		Predicate qLabelPredicate = null;
		Predicate qTypePredicate = null;
		if (null != dict) {
			if (StringHelper.isNotBlank(dict.getLabel())) {
				qLabelPredicate = qDict.label.like("%" + dict.getLabel().trim() + "%");
			}
			if (StringHelper.isNotBlank(dict.getType())) {
				qTypePredicate = qDict.type.like("%" + dict.getType().trim() + "%");
			}
		}

		Predicate predicate = qDict.id.goe(0).and(qTypePredicate).and(qLabelPredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<Dict> pageList = dictRepository.findAll(predicate, pageRequest);
		return PageUtils.of(pageList);
	}


	@Override
	@Cacheable(key = "'dict_list'")
	public List<Dict> getAllList() {
		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).fetch();
	}

	@Override
	@Cacheable(key = "'dict_' + #id")
	public Dict findById(Integer id) {
		if (null == id || id < 0) return null;

		return dictRepository.findOne(id);
	}

	@Override
	@Cacheable(key = "'dict_' + #type")
	public List<Dict> getDictList(String type) {
		List<Dict> dictList = new ArrayList<Dict>();
		if (StringHelper.isBlank(type)) return dictList;

		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).where(qDict.type.eq(type.trim())).fetch();
	}

	@Override
	@CacheEvict(allEntries = true)
	@Transactional
	public Dict saveOrUpdate(Dict dict) {
		if (null == dict) return null;

		Dict dbDict = this.dictRepository.saveAndFlush(dict);

		return dbDict;
	}

	@Override
	@CacheEvict(allEntries = true)
	@Transactional
	public boolean delById(Integer id) {
		if (null == id || id <= 0) return Boolean.FALSE;

		QDict qDict = QDict.dict;
		long num = this.queryFactory.update(qDict).set(qDict.statu, 1) // 0 正常 1删除
				.where(qDict.id.eq(id.intValue())).execute();

		return num > 0;
	}
}
