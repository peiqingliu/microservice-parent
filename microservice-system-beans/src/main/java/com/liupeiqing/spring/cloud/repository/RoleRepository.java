package com.liupeiqing.spring.cloud.repository;


import com.liupeiqing.spring.cloud.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>,
		QueryDslPredicateExecutor<Role> {

	Role findRoleByRoleCode(String roleCode);

}
