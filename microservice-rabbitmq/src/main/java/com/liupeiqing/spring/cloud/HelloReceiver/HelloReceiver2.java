package com.liupeiqing.spring.cloud.HelloReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 测试一对多
 * @author liupeiqing
 * @data 2018/7/18 9:45
 */
@Component
@RabbitListener(queues = "hello")  //监听hello队列
public class HelloReceiver2 {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver2  : " + hello);
    }
}
