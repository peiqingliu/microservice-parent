package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * @author liupeiqing
 * @data 2018/8/27 20:34
 */
public interface OrderService {

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
