package com.wuqianqian.rocket.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author liupeqing
 * @date 2018/10/25 17:10
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        //生产者,可以指定producer集群
        //1.创建一个生产者,需要指定Producer的分组，
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_name");
        //设置name server的地址
        //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码
        //2.设置命名服务的地址,默认是去读取conf文件下的配置文件 rocketmq.namesrv.addr
        producer.setNamesrvAddr("10.11.67.28:9876");
        //3.启动生产者
        producer.start();
        System.out.println(producer.getNamesrvAddr());
        System.out.println(producer.getClientIP());
        System.out.println("启动了生产者producer");
        //message必须指定topic,和消息体body
        // 可以选择指定tag,key来进行细分message
        //4.最基本的生产模式 topic+文本信息
        Message msgA = new Message("topicA", "这是topicA的消息,没有指定tag和key".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msgB = new Message("topicB", "这是topicB的消息,没有指定tag和key".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msgC = new Message("topicC", "tag-a", "这是topicC的消息,指定了tag-a".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msgD = new Message("topicC", "tag-b", "这是topicC的消息,指定了tag-b".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msgE = new Message("topicC", "tag-a", "key1", "这是topicC的消息,指定了tag-a和key1".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msgF = new Message("topicC", "tag-a", "key2", "这是topicC的消息,指定了tag-a和key2".getBytes(RemotingHelper.DEFAULT_CHARSET));


        Message[] messages = new Message[]{msgA, msgB, msgC, msgD, msgE,msgF};
        //发送消息
        for (Message message : messages) {
            //调用producer的send()方法发送消息
            //这里调用的是同步的方式，所以会有返回结果
            //5.获取发送响应
            SendResult result = producer.send(message);
            System.out.println("消息发送成功:id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());
        }
        //6.释放生产者
        producer.shutdown();
    }

}
