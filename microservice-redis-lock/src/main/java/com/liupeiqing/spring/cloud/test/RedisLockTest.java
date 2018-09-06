package com.liupeiqing.spring.cloud.test;



/**
 * @author liupeiqing
 * @data 2018/9/1 16:27
 */
public class RedisLockTest {


    public static void main(String[] args) {

        for (int i = 1; i < 10; i ++) {
            new TestLock().start();
        }

    }


}
