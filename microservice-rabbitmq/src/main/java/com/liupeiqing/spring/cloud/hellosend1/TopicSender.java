package com.liupeiqing.spring.cloud.hellosend1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * topic ExChange示例
 *
 * topic 是RabbitMQ中最灵活的一种方式，可以根据binding_key自由的绑定不同的队列
 *
 * 首先对topic规则配置，这里使用两个队列来测试（也就是在Application类中创建和绑定的topic.message和topic.messages两个队列），其中topic.message的bindting_key为
 *
 * “topic.message”，topic.messages的binding_key为“topic.#”；
 *
 * @author liupeiqing
 * @data 2018/7/18 11:54
 */
@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息 是往交换机上发送 但是交换机不会存储消息 会根据匹配规则发送到队列上面
     */
    public void sendTopic(){
        String msg1 = "I  AM  topic.mesaage 11111111msg=====================";
        //第一个参数表示交换机，第二个参数表示routing key，第三个参数即消息。
        this.amqpTemplate.convertAndSend("exchange","topic.message",msg1);


    }

    public void sendTopic2(){
        String msg2 = "I am topic.mesaages 222222222 msg########**************";
        System.out.println("sender2 : " + msg2);
        this.amqpTemplate.convertAndSend("exchange", "topic.messages", msg2);
    }
}
