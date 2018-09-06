package com.liupeiqing.spring.cloud.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/8/20 11:26
 */
@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //自增
    private Long id;

    @Column
    private String username;

    @Column
    private Integer age;
}
