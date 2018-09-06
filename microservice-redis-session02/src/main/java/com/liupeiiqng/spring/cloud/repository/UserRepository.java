package com.liupeiiqng.spring.cloud.repository;


import com.liupeiiqng.spring.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liupeiqing
 * @data 2018/8/8 20:16
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    

}
