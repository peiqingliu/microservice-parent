package com.liupeiqing.spring.cloud.thread;

import lombok.Data;

/**
 * @author liupeiqing
 * @data 2018/8/31 12:30
 */
@Data
public class RunableTest implements Runnable {

    private String name;

    public RunableTest(String name){
        this.name = name;
    }

    public RunableTest() {

    }

    @Override
    public void run() {
        //写自己的业务代码
        System.out.println("当前执行线程的名字"+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Runnable r1 = new RunableTest("A");
        Runnable r2 = new RunableTest("B");

        Thread thread1 = new Thread(r1);
        thread1.start();
        Thread thread2 = new Thread(r2);
        thread2.start();
    }
}
