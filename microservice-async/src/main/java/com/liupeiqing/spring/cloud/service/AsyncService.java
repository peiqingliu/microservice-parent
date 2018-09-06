package com.liupeiqing.spring.cloud.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 执行异步任务的接口
 * @author liupeiqing
 * @data 2018/8/10 17:16
 */
public interface AsyncService {

    /**
     * 执行异步任务
     */
    void execuAsync1() throws InterruptedException;

    void execuAsync2() throws InterruptedException;

    Future<String> execuAsync3() throws InterruptedException, ExecutionException;  //带有返回值的异步方法
}
