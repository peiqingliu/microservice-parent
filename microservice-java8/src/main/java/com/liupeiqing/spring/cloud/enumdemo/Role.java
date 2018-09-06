package com.liupeiqing.spring.cloud.enumdemo;

import lombok.Data;

/**
 * @author liupeiqing
 * @data 2018/8/21 11:45
 *
 * 角色枚举
 */

public enum Role {
    //后面有成员变量的时候，加分号
    //ROLE_ADMIN 就相当于 Role的实例对象
    ROLE_ADMIN("管理员",1),ROLE_TEACHER("教师",2),ROLE_STUDENT("学生",3);

    //成员变量
    private String name;

    private int index;

    //构造方法 默认是私有的
    Role(String name,int index){
        this.name = name;
        this.index=index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
