package com.liupeiqing.spring.cloud.HelloReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 12:09
 */
@Component
@RabbitListener(queues = "topic.messages")
public class topicMessagesReceiver {

    @RabbitHandler
    public void process(String msg){
        System.out.print("messages   我是2"+msg);
    }
}
