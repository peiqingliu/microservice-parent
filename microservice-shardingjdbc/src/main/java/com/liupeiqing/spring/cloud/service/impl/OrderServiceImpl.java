package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.domain.Order;
import com.liupeiqing.spring.cloud.mapper.OrderMapper;
import com.liupeiqing.spring.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liupeiqing
 * @data 2018/8/27 20:35
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void insert(Order order) {
        this.orderMapper.insert(order);
    }

    @Override
    public List<Order> findByUserId(Integer id) {
        return this.orderMapper.findByUserId(id);
    }

    @Override
    public List<Order> findByUserIdBetween(Map<String, Integer> map) {
        return null;
    }

    @Override
    public List<Order> findByOrderId(Integer id) {
        return this.orderMapper.findByUserId(id);
    }
}
