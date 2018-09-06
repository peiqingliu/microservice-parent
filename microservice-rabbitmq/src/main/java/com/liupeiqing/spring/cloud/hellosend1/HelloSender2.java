package com.liupeiqing.spring.cloud.hellosend1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试多生产者对应多消费者
 * @author liupeiqing
 * @data 2018/7/18 10:06
 */
@Component
public class HelloSender2 {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String str){
        this.amqpTemplate.convertAndSend("hello",str);
    }
}
