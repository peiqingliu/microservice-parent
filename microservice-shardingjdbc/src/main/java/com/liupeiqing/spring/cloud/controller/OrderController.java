package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.domain.Order;
import com.liupeiqing.spring.cloud.repository.OrderRepository;
import com.liupeiqing.spring.cloud.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/8/24 13:59
 */
@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/save/order")
    public String saveOrder(@RequestBody Order order){
        this.orderServiceImpl.insert(order);
        return "success";
    }
}
