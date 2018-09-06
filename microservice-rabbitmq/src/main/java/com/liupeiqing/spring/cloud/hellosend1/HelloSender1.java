package com.liupeiqing.spring.cloud.hellosend1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息生产者
 * @author liupeiqing
 * @data 2018/7/17 20:05
 */
@Component
public class HelloSender1 {

    public final static Logger log = LoggerFactory.getLogger(HelloSender1.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String data){
        String senMsg = "hello1" + new Date() +data;
        log.info("sender1"+  senMsg + data);
        //convertAndSend(exchangeName, queueName, msg)
        //链接hello这个queue，并发送消息
        this.rabbitTemplate.convertAndSend("hello",senMsg);
    }
}
