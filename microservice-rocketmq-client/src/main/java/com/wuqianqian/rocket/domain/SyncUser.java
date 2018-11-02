package com.wuqianqian.rocket.domain;

import lombok.Data;

/**
 * 同步的用户
 * @author liupeqing
 * @date 2018/10/26 15:58
 */
@Data
public class SyncUser {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 职位
     */
    private String jobName;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 学校id(组织)
     */
    private String schoolId;

    /**
     * 用户状态
     */
    private String status;

    /**
     * 登录账号
     */
    private String identifier;
}
