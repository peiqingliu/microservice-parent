package com.liupeiqing.spring.cloud.stream;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liupeqing
 * @date 2018/11/19 18:48
 */
@Data
public class Apple {
    int id;
    BigDecimal pri;
    String name;
    int num;

    public Apple(int id,String name,BigDecimal pri,int num){
        this.id = id;
        this.name = name;
        this.pri = pri;
        this.num =num;
    }

}
