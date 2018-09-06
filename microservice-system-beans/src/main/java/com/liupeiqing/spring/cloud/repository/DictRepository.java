package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface DictRepository extends JpaRepository<Dict, Integer>,
        QueryDslPredicateExecutor<Dict> {

}
