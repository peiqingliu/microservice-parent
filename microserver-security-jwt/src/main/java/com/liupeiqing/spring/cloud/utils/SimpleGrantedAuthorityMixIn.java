package com.liupeiqing.spring.cloud.utils;

/**
 * @author liupeiqing
 * @data 2018/8/18 11:50
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixIn {

    public SimpleGrantedAuthorityMixIn(@JsonProperty("rol") String authority) {
    }

}