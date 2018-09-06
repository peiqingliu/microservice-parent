package com.liupeiqing.spring.cloud.repository;


import com.liupeiqing.spring.cloud.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface MenuRepository extends JpaRepository<Menu, Integer>,
        QueryDslPredicateExecutor<Menu> {

}
