package com.liupeiqing.spring.cloud.authbeans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/9/5 17:10
 */
@Data
public class AuthPermission implements Serializable {
    private static final long	serialVersionUID	= 4566420419542436770L;

    /**
     * 请求URL
     */
    private String				url;

    public AuthPermission() {

    }

    public AuthPermission(String url) {
        this.url = url;
    }
}
