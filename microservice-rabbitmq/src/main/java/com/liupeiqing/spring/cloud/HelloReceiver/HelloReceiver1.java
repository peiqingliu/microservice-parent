package com.liupeiqing.spring.cloud.HelloReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接受者
 * @author liupeiqing
 * @data 2018/7/17 20:14
 */
@Component
@RabbitListener(queues = "hello")  //监听queue
public class HelloReceiver1 {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver1  : " + hello);
    }

}
