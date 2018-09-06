package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liupeiqing
 * @data 2018/8/24 13:56
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
