package com.liupeiqing.spring.cloud.repository;


import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>,
		QueryDslPredicateExecutor<User> {

	User findUserByUsername(String username);

	User findUserByMobile(String mobile);

	User findUserByOpenId(String openId);

	User findUserByUserId(Integer userId);

}
