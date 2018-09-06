package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author liupeiqing
 * @data 2018/8/16 16:28
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByUsername(String username);
  //  Collection<GrantedAuthority> loadUserAuthorities(String username);

}
