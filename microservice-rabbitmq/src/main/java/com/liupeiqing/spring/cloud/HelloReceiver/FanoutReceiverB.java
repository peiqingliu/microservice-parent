package com.liupeiqing.spring.cloud.HelloReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 14:25
 */
@RabbitListener(queues = "fanout.B")
@Component
public class FanoutReceiverB {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("FanoutReceiverB  : " + msg);
    }
}
