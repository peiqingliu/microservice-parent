package com.liupeiqing.spring.cloud.AsyncTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author liupeiqing
 * @data 2018/8/13 13:44
 *
 * 想起spring对@Transactional注解时也有类似问题，spring扫描时具有@Transactional注解方法的类时，是生成一个代理类，
 * 由代理类去开启关闭事务，而在同一个类中，方法调用是在类体内执行的，spring无法截获这个方法调用。
 *
 * 写的异步任务，不能和调用的 在同一个类体内
 */
@Slf4j
@Component
public class AsyncTask {

    /**
     * 模拟业务
     * @throws InterruptedException
     */
    @Async(value = "asyncExecutor")
    public void task1() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis1 = System.currentTimeMillis();
        log.info("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");  //输出  显示不同的线程池
        System.out.println("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async(value = "myAsync")
    public void task2() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        log.info("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
        System.out.println("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async(value = "myAsync")
    public Future<String> task3() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(3000);
        long currentTimeMillis1 = System.currentTimeMillis();
        log.info("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
        System.out.println("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
        return new AsyncResult<String>("task3执行完毕!");
    }
}
