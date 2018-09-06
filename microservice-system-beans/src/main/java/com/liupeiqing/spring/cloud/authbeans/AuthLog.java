package com.liupeiqing.spring.cloud.authbeans;

import java.io.Serializable;

/**
 * @author liupeiqing
 * @data 2018/9/5 17:09
 */
public class AuthLog implements Serializable {

    private static final long	serialVersionUID	= -7612739305546935933L;

    private Log					log;

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
