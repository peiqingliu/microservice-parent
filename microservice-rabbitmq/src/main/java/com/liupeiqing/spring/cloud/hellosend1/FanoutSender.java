package com.liupeiqing.spring.cloud.hellosend1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 14:21
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String msgString="fanoutSender :hello i am liupeiqing";
        System.out.println(msgString);
        this.amqpTemplate.convertAndSend("fanoutExchange","abcd.ee", msgString);
    }
}
