package com.liupeiqing.spring.cloud.repository;


import com.liupeiqing.spring.cloud.domain.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * JpaRepository<LogInfo, Long>,继承标记接口  需给出实体类名，和主键类型
 * @author liupeiqing
 *
 * 2018年6月14日
 */
public interface LogInfoRepository extends JpaRepository<LogInfo, Long>,
        QueryDslPredicateExecutor<LogInfo> {

}
