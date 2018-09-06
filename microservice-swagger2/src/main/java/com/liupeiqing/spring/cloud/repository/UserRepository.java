package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/20 11:31
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "update User set username=:username where id =:id")
    User update(@Param("username") String username, @Param("id") Long id);

    @Query("select u from User u where u.username =:name or u.age =:age")
    List<User> findByName2(@Param("name") String name, @Param("age") int age);

}
