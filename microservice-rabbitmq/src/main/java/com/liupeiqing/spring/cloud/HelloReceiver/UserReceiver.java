package com.liupeiqing.spring.cloud.HelloReceiver;

import com.liupeiqing.spring.cloud.domain.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/18 11:43
 */
@Component
@RabbitListener(queues = "user")
public class UserReceiver {

    @RabbitHandler
    public void provess(User user){
        System.out.print("接受实体类user"+user);
    }
}
