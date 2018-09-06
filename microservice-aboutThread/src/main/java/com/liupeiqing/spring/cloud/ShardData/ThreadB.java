package com.liupeiqing.spring.cloud.ShardData;

/**
 * @author liupeiqing
 * @data 2018/8/31 17:00
 */
public class ThreadB extends Thread {

    /**
     * 此时不加static 不会是共享数据  因为在new ThreadB()  分配在堆内的时候，每个实例对象都会有一个Apple_Counts = 10的数据
     *
     * 如果加上static  数据会分配至方法区  被该类的每个实例对象共享
     */
    private static int Apple_Counts=10;//共享数据-苹果个数

    private static int Orange_Counts=1;//共享数据-苹果个数

    /**
     * 同时起了三个线程，
     * 1.对于共享数据如果不加锁  则会产生线程不安全的情况
     * 2.加 synchronized (this)  发现还是会有问题 ，因为此时synchronized 锁住的是this 对象
     * 而此时的this是三个线程的实例对象，分别为b1,b2,b3，所以，synchronized 起作用的只是针对某一个实例对象
     * 3.synchronized (ThreadB.class) 使用同步锁对象是：ThreadB.class
     */

    @Override
    public void run() {
        synchronized (ThreadB.class){

            while(Apple_Counts>0){
                if (Apple_Counts>0) {
                    try {
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName()+"卖了一个苹果，还剩"+--Apple_Counts);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }
//
//    public void testSynchronized(){
//        while(Orange_Counts > 0 ){
//            if (Orange_Counts < 5) {
//                try {
//                    Thread.sleep(10);
//                    System.out.println(Thread.currentThread().getName()+"买了一个橘子，还有"+ ++Orange_Counts);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    public static void main(String[] args) {
        ThreadB b1=new ThreadB();
        ThreadB b2=new ThreadB();
        ThreadB b3=new ThreadB();
        b1.start();
        b2.start();
        b3.start();
    }
}
