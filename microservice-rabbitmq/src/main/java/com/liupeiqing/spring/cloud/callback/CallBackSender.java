package com.liupeiqing.spring.cloud.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *
 * Spring AMQP提供了一个发送和接收消息的操作模板类AmqpTemplate。
 * AmqpTemplate它定义包含了发送和接收消息等的一些基本的操作功能。RabbitTemplate是AmqpTemplate的一个实现。
 *
 * RabbitTemplate支持消息的确认与返回，为了返回消息，RabbitTemplate 需要设置mandatory 属性为true,
 * 并且CachingConnectionFactory 的publisherReturns属性也需要设置为true。
 * 返回的消息会根据它注册的RabbitTemplate.ReturnCallback setReturnCallback 回调发送到给客户端，
 *
 * 一个RabbitTemplate仅能支持一个ReturnCallback 。
 *
 * 为了确认Confirms消息, CachingConnectionFactory 的publisherConfirms 属性也需要设置为true，
 * 确认的消息会根据它注册的RabbitTemplate.ConfirmCallback setConfirmCallback回调发送到给客户端。
 *
 * 一个RabbitTemplate也仅能支持一个ConfirmCallback.
 * @author liupeiqing
 * @data 2018/7/18 15:21
 */
@Component
public class CallBackSender implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        rabbitTemplate.setConfirmCallback(this);
        String msg="callbackSender : i am callback sender";
        System.out.println(msg);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend("exchange","topic.messages",msg,correlationData);
    }

    /**
     * 此处是收到的回应
     * 从上面可以看出callbackSender发出的UUID，收到了回应，又传回来了。
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

        System.out.println("callbakck confirm: " + correlationData.getId());

    }
}
