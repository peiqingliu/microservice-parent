package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface DeptRepository extends JpaRepository<Dept, Integer>,
        QueryDslPredicateExecutor<Dept> {

}
