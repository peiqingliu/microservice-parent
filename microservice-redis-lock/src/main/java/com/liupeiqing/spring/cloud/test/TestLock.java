package com.liupeiqing.spring.cloud.test;

import com.liupeiqing.spring.cloud.redisLock.RedisLock;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author liupeiqing
 * @data 2018/9/1 17:24
 * 此处测试  有问题
 */
public class TestLock extends Thread {


    @Override
    public void run() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        RedisLock redisLock = new RedisLock(jedisConnectionFactory);

        redisLock.tryLock("test1", "qwerqwer");

        // TODO 模拟业务
        System.out.println(Thread.currentThread().getName() + " get lock.....");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " release lock.....");

        redisLock.unlock( "test1", "qwerqwer");
    }

}
