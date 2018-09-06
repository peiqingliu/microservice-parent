package com.liupeiqing.spring.cloud.HelloReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 12:07
 */
@Component
@RabbitListener(queues = "topic.message")
public class TopReservicer {

    @RabbitHandler
    public void reservice(String str){
        System.out.print("message  我是1"+str);
    }
}
