package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author liupeiqing
 * @data 2018/8/10 17:23
 */
@Slf4j
@RestController
public class AsyncController {


    @Autowired
    private AsyncService asyncService;

    @GetMapping("/execute")
    public String execute() throws InterruptedException, ExecutionException {
        log.info("start execute");
        long currentTimeMillis = System.currentTimeMillis();
        this.asyncService.execuAsync1();
        this.asyncService.execuAsync2();
        Future<String> str = this.asyncService.execuAsync3();
        log.info("end execute");
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms") ;
        return "success" + str.get();
    }
}
