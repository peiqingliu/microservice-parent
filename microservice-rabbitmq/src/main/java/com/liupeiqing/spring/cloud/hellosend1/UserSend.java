package com.liupeiqing.spring.cloud.hellosend1;

import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 11:42
 */
@Component
public class UserSend {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(User user){
        amqpTemplate.convertAndSend("user",user);
    }
}
