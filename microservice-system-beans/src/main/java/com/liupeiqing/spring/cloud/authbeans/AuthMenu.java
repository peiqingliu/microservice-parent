package com.liupeiqing.spring.cloud.authbeans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/9/5 17:10
 */
@Data
public class AuthMenu implements Serializable {

    private static final long	serialVersionUID	= 4566420419542436770L;

    /**
     * 菜单id
     */
    private Integer				menuId;
    /**
     * 菜单名称
     */
    private String				menuName;
    /**
     * 请求路径
     */
    private String				path;
    /**
     * 请求链接
     */
    private String				url;
    /**
     * 父菜单ID
     */
    private Integer				pid;
    /**
     * 0-正常，1-删除
     */
    private Integer				statu				= 0;
}
