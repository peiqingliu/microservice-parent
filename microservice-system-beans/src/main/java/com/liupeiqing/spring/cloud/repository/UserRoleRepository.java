package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>,
        QueryDslPredicateExecutor<UserRole> {

}
