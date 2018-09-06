package com.liupeiqing.spring.cloud.thread;

import lombok.Data;

/**
 * @author liupeiqing
 * @data 2018/8/31 12:11
 * 通过 继承thread  实现多线程
 */


public class ThreadTest extends Thread {

    private String name;

    public ThreadTest(String name){
        super(name);
    }

    @Override
    public void run() {
        super.run();
        //写自己的业务代码
        System.out.println("当前执行线程的名字"+Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        ThreadTest t1 = new ThreadTest("A");
        ThreadTest t2 = new ThreadTest("B");

        t1.start();
        t2.start();
    }

}
