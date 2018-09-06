package com.liupeiqing.spring.cloud.methodcitation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/18 15:44
 */
public class Java8Tester {
    public static void main(String[] args) {
        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");
        names.forEach(System.out :: print);
    }
}
