package com.liupeiqing.spring.cloud.executors;

import java.util.concurrent.*;

/**
 * @author liupeiqing
 * @data 2018/8/31 19:05
 *
 * 线程池详解
 * Executor：是Java线程池的超级接口；提供一个execute(Runnable command)方法;我们一般用它的继承接口ExecutorService。
 *
 * Executors：是java.util.concurrent包下的一个类，提供了若干个静态方法，用于生成不同类型的线程池。Executors一共可以创建下面这四类线程池：
 *
 * newFixedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPool 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
 * newSingleThreadExecutor 创建一个使用单个 worker 线程的 Executor 线程池，以无界队列方式来运行该线程。它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * ExecutorService：它是线程池定义的一个接口，继承Executor。 线程池的主要接口
 * 有两个实现类，分别为ThreadPoolExecutor, 线程池，创建的线程池  主要用它 来创建
 * 可以通过调用Executors以下静态工厂方法来创建线程池并返回一个ExecutorService对象：
 *
 * ScheduledThreadPoolExecutor。
 *
 * public ThreadPoolExecutor(int corePoolSize,  //核心线程数  如果当前运行的线程数 小于核心线程数，则创建新线程来执行任务，即使线程池中有空的线程
 *                           int maximumPoolSize,//最大线程数，允许创建的最大线程数。corePoolSize和maximumPoolSize  设置的边界自动调整线程池大小
 *                                                  corePoolSize <运行的线程数< maximumPoolSize:仅当队列满时才创建新线程
 *                                                  corePoolSize=运行的线程数= maximumPoolSize：创建固定大小的线程池
 *                           long keepAliveTime,//如果当前的线程数大于核心线程数，则这些多余的线程空闲时间大于keepAliveTime，则会被终止。
 *                           TimeUnit unit,  //keepAliveTime 时间参数单位
 *                           BlockingQueue<Runnable> workQueue,//保存任务的阻塞队列，与线程池有关。
 *                           如果当前线程数少于核心线程数，在有新任务的时候，直接创建线程来执行任务，而不用放进队列
 *                           如果当前线程等于或者大于核心线程数的时候，在有新任务来的时候，把任务放进队列中，不直接创建线程，当队列满的时候，才会创建线程。
 *                           ThreadFactory threadFactory, // 使用ThreadFactory创建新线程，默认使用defaultThreadFactory创建线程
 *                           RejectedExecutionHandler handler) //定义处理被拒绝任务的策略，默认使用ThreadPoolExecutor.AbortPolicy,任务被拒绝时将抛出RejectExecutorException
 *                           //后两个参数为可选参数
 *
 * 阿里手册：
 * 线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，
 * 这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
 *
 *
 * ExecutorService常用的几个方法：
 *
 * execute(Runnable)从父类继承过来的方法  方法接收一个Runnable实例，并且异步的执行
 * submit(Runnable)  返回一个Future对象，通过返回的Future对象，我们可以检查提交的任务是否执行完毕。
 * submit(Callable)
 * invokeAny(...)
 * invokeAll(...)
 * shutdown()  我们使用完成ExecutorService之后应该关闭它，否则它里面的线程会一直处于运行状态。
 *
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) {
        ExecutorServiceDemo executorServiceDemo = new ExecutorServiceDemo();
       // executorServiceDemo.testNewSingleThreadExecutor();
       // executorServiceDemo.textNewFixedThreadPool();
        executorServiceDemo.testNewCachedThreadPool();
    }

    //测试 单线程的线程池
    public void testNewSingleThreadExecutor(){
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();  //创建一个单线程池

        //等价与上面一个 创建一个单线程的线程池   corePoolSize和maximumPoolSize都等于，表示固定线程池大小为1
        ExecutorService executorService2 =
                new ThreadPoolExecutor(1,1,0L,TimeUnit.MICROSECONDS,new LinkedBlockingDeque<Runnable>());
        //循环5次 但是始终还是一个 线程在执行任务
        for (int i=0;i<5;i++){
            final  int task = i;  //为什么 非要用task转一下，而不是直接输出i 用final是因为
            // new Runnable() 相当于匿名内部类 智能使用一次，而且使用外部变量的时候，必须是不可变的 finnal标注
            executorService2.execute(new Runnable() { //接收一个Runnable实例
                public void run() {
                    System.out.println("线程name为"+Thread.currentThread().getName()+"执行Asynchronous task" + task);
                }
            });
        }
        executorService2.shutdown();
    }

    //测试固定大小线程的线程池
    //创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
    //线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
    public void textNewFixedThreadPool(){
        //创建一个 线程数为3 的线程
        ExecutorService pool = Executors.newFixedThreadPool(3);

        //等同于 上面一种
        ExecutorService pool2 =
                new ThreadPoolExecutor(3,3,0L,TimeUnit.MICROSECONDS,new LinkedBlockingQueue<Runnable>());

        //此时创建了 5个任务 由3个线程分别去执行
        for (int i=0;i<5;i++){
            final  int task = i;
            pool2.execute(new Runnable() { //接收一个Runnable实例
                public void run() {
                    System.out.println("线程name为"+Thread.currentThread().getName()+"执行Asynchronous task" + task);
                }
            });
        }
        pool2.shutdown();
    }

    //创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
    //那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
    //此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
    public void testNewCachedThreadPool(){

        ExecutorService pool = Executors.newCachedThreadPool();

        //new SynchronousQueue<Runnable>()使用同步队列，将任务直接提交给线程
        ExecutorService pool2 =
                new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,TimeUnit.MICROSECONDS,new SynchronousQueue<Runnable>());

        //多少任务 就会有多少线程 去执行
        for (int i = 1; i <= 5; i ++) {
            final  int task = i;   //5个任务
            //TimeUnit.SECONDS.sleep(1);
            pool2.execute(new Runnable() {    //接受一个Runnable实例
                public void run() {
                    System.out.println("线程名字： " + Thread.currentThread().getName() +  "  任务名为： "+task);
                }
            });
        }

    }

    public void testNewScheduledThreadPool(){

    }

}
