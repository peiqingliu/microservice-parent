package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.dao.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.liupeiqing.spring.cloud.domain.User;
import java.io.Serializable;
import java.util.List;

/**
 *
 * 这里的UserRepository继承了2个父类，包括用户自定义的接口类，让用户自定义的接口可以暴漏出来。自定义的接口在1.5.9版本有问题
 * @author liupeiqing
 * @data 2018/7/23 16:58
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    //自定义接口方法
//    @Query
//    public List<User> getAllUser();

}
