package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.dao.UserDaoCustom;
import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 基础的 Repository 提供了最基本的数据访问功能，其几个子接口则扩展了一些功能。它们的继承关系如下：
 * Repository： 仅仅是一个标识，表明任何继承它的均为仓库接口类
 * （1） CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法
 * （2）PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法
 * （3）JpaRepository： 继承 PagingAndSortingRepository，实现一组 JPA 规范相关的方法
 *
 * 自定义的 XxxxRepository 需要继承 JpaRepository，这样的 XxxxRepository 接口就具备了通用的数据访问控制层的能力。
 *
 * JpaSpecificationExecutor： 不属于Repository体系，实现一组 JPA Criteria 查询相关的方法 。
 *
 * @author liupeiqing
 * @data 2018/7/17 14:58
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>,CrudRepository<User,Long>,JpaSpecificationExecutor<User> {
    /**
     * 一个参数，匹配两个字段
     * @param name
     * @return
     * 这里的%只能放在占位的前面，后面不行
     */
    @Query("select u from User u where u.username =:name or u.age =:age")
    List<User> findByName2(@Param("name") String name, @Param("age") int age);

    User findByUsername(String username);
}
