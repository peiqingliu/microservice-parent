package com.wuqianqian.rocket.client;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author liupeqing
 * @date 2018/10/25 18:36
 */
public class PushConsumer {


    /**
     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
     */
    public static void main(String[] args) throws MQClientException {

        //定义消费者,可以指定消费集群
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group_name");
        //同样的,指定name server 的地址
        consumer.setNamesrvAddr("127.0.0.1:9876");

      /*
        //订阅topicA下的所有消息
        consumer.subscribe("topicA","*");
        //一个consumer可以订阅多个topic
        consumer.subscribe("topicB","*");
        */

        consumer.subscribe("topicC","*");

        //程序第一次启动从消息队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //注册订阅消息
        consumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(
                            List<MessageExt> list,
                            ConsumeConcurrentlyContext Context) {
                        MessageExt msg = list.get(0);
                        try {
                            System.out.println( new Date()
                                    +"-收到消息:id-"+msg.getMsgId()
                                    +","+ new String(msg.getBody(), "UTF-8")
                                    +","+"keys: "+msg.getKeys()
                            );
                            System.out.println("msg全部信息:"+ msg.toString());

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
        );
        consumer.start();
        System.out.println("consumer消费者启动");

//        /**
//         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
//         * 注意：ConsumerGroupName需要由应用来保证唯一
//         */
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
//
//        consumer.setNamesrvAddr("10.11.67.28:9876");
//        consumer.setInstanceName("Consumber");
//
//        //这里设置的是一个consumer的消费策略
//        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
//        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
//        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//
//        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
//        try {
//            consumer.subscribe("topic_orderCreate", "*");
//            //设置一个Listener，主要进行消息的逻辑处理
//            consumer.registerMessageListener(new MessageListenerConcurrently() {
//                @Override
//                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//
//                    System.out.println(Thread.currentThread().getName()
//                            +" Receive New Messages: " + msgs.size());
//
//                    MessageExt msg = msgs.get(0);
//                    if (null != msg){
//
//                        System.out.println(msg.getBody().toString());
//                    }
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            });
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//        /**
//         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
//         */
//        consumer.start();
//
//        System.out.println("ConsumerStarted.");
    }
}
