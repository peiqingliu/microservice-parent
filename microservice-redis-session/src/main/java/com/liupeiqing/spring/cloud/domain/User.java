package com.liupeiqing.spring.cloud.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //自增
    private Long id;

    @Column
    private String username;

    @Column
    private Integer age;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
