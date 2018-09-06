package com.liupeiqing.spring.cloud.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/8/16 15:36
 */
@Entity
@Table(name = "jd_user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
