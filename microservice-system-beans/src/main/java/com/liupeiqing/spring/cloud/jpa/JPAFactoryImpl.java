package com.liupeiqing.spring.cloud.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

public abstract class JPAFactoryImpl {

	// JPA查询工厂
	protected JPAQueryFactory queryFactory;

	@Autowired
	private EntityManager		em;

	@PostConstruct
	public void initFactory() {
		queryFactory = new JPAQueryFactory(em);
	}

}
