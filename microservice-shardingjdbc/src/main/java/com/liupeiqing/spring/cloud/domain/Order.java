package com.liupeiqing.spring.cloud.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/8/24 11:54
 */

public class Order implements Serializable {


    private Long id;

    private Integer userId;
    private Integer orderId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }
}
