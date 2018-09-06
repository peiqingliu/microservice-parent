package com.liupeiqing.spring.cloud.ShardData;

/**
 * @author liupeiqing
 * @data 2018/8/31 13:45
 * 被共享的数据
 */
public class ShardData {
    private int i = 10;

    public void increment() throws InterruptedException {
        synchronized (this){
            while (i > 0){
                Thread.sleep(100);
                i--;
                System.out.println("由线程" + Thread.currentThread().getName() +"计算的数字为" + i);
            }
        }
    }
}
