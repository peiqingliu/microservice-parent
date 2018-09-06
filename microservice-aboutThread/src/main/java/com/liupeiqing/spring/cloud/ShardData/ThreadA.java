package com.liupeiqing.spring.cloud.ShardData;

/**
 * @author liupeiqing
 * @data 2018/8/31 13:51
 * 实现数据共享
 */
public class ThreadA implements Runnable {

    private ShardData shardData;

    public ThreadA(ShardData data) {
        this.shardData = data;
    }

    @Override
    public void run() {
        try {
            shardData.increment();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ShardData shardData = new ShardData();
        /**
         * 其中 new ThreadA()是被分在堆内存上的，new一次 ，就会在堆内存上给其分配空间，堆内存空间必须使用new关键字才能开辟
         * t1 是分配在栈内存中，为 new ThreadA()堆内存的访问地址
         * 同理 t2也是 ，栈中的变量指向堆内存中的变量，这就是 Java 中的指针！
         * 因为t1和t2指向的是不同的内存地址，所以两者是不同的。
         *
         * 在java虚拟机中 方法区放的是类本身的一些信息，其中包含了 常量，静态变量常量池 -》用于存放编译期生成的各种字面量和符号引用，
         * 这部分内容将在类加载后存放到方法区的运行时常量池中。等
         *  类中的变量 也是放在堆内存上的
         *
         *  方法区  堆内存是线程共享的
         *
         *  而虚拟机栈，本地方法栈和程序计数器则是线程私有的
         */
        /**
         * shardData 是 同一个实例对象 属于线程共享
         * 此时就会存在 如果是多个线程的话对共享的数据进行 操作  就会出现问题。
         * 此时应该在 操作共享数据的地方加锁
         */
        ThreadA t1 = new ThreadA(shardData);
        ThreadA t2 = new ThreadA(shardData);

        //这两个实例对象  指向的不同的内存地址
        System.out.println(t1 == t2);  //false
        System.out.println(t1.equals(t2));  //false

       new Thread(t1).start();
       new Thread(t2).start();
    }
}
