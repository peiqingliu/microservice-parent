package com.liupeiqing.spring.cloud.mapper;

import com.liupeiqing.spring.cloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liupeiqing
 * @data 2018/8/27 20:26
 */
@Mapper
@Repository
public interface OrderMapper {

    /**
     * insert
     *
     * @param order
     */
    void insert(Order order);

    /**
     * userID
     *
     * @param id
     * @return
     */
    List<Order> findByUserId(Integer id);

    List<Order> findByUserIdBetween(Map<String, Integer> map);

    /**
     * orderID
     *
     * @param id
     * @return
     */
    List<Order> findByOrderId(Integer id);

}
