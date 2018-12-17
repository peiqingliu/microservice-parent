package com.liupeiqing.springcloud.model;

import lombok.Data;

/**
 * 使用这种映射 实体类的字段必须和数据库一致
 * @author liupeqing
 * @date 2018/12/17 17:14
 */
@Data
public class User {
    private Integer user_id;

    private String user_name;

    private String password;

    private String phone;
}
