package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.AsyncTask.AsyncTask;
import com.liupeiqing.spring.cloud.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author liupeiqing
 * @data 2018/8/10 17:17
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private AsyncTask asyncTask;


    @Override
    public void execuAsync1() throws InterruptedException {
        this.asyncTask.task1();
    }

    @Override
    public void execuAsync2() throws InterruptedException {
        this.asyncTask.task2();

    }

    @Override
    public Future<String> execuAsync3() throws InterruptedException {
        Future<String> stringFuture = this.asyncTask.task3();
        return stringFuture;
    }
}
