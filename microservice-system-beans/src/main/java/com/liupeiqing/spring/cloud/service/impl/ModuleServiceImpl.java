package com.liupeiqing.spring.cloud.service.impl;


import com.liupeiqing.spring.cloud.cache.AdminCacheKey;
import com.liupeiqing.spring.cloud.domain.Module;
import com.liupeiqing.spring.cloud.domain.QModule;
import com.liupeiqing.spring.cloud.jpa.JPAFactoryImpl;
import com.liupeiqing.spring.cloud.service.ModuleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@CacheConfig(cacheNames = AdminCacheKey.MODULE_INFO)  //@CacheConfig是一个类级别的注解，允许共享缓存的名称
@Component
public class ModuleServiceImpl extends JPAFactoryImpl implements ModuleService {

	@Override
	@Cacheable(key = "'module_list'")
	public List<Module> getAllList() {
		QModule qModule = QModule.module;

		return this.queryFactory.selectFrom(qModule).fetch();
	}

}
