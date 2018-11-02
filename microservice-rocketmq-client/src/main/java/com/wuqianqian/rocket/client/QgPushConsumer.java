package com.wuqianqian.rocket.client;

import com.alibaba.fastjson.JSON;
import com.wuqianqian.rocket.domain.SyncUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author liupeqing
 * @date 2018/10/26 15:42
 */
@Slf4j
public class QgPushConsumer {
    public static void main(String[] args) {
        //定义消费者,可以指定消费集群
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group_name");
        //同样的,指定name server 的地址
        consumer.setNamesrvAddr("localhost:9876");
        try {
            consumer.subscribe("base_info_qg_anfang","*");
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            //注册订阅消息

            //消费消息   事务消费 MessageListenerConcurrently
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    try {
                        MessageExt msg = list.get(0);
                        String tag = msg.getTags();
                        String userJson = new String(msg.getBody(), "UTF-8");
                        jsonToBean(userJson,tag);
                        System.out.println( new Date()
                                +"-收到消息:id-"+msg.getMsgId()
                                +","+ new String(msg.getBody(), "UTF-8")
                                +","+"keys: "+msg.getKeys()
                        );
                        System.out.println("msg全部信息:"+ msg.toString());

                    } catch (UnsupportedEncodingException e) {
                        log.error("获取消息出现错误" + e.getMessage());
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });


            consumer.start();
            System.out.println("consumer消费者启动");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将json转换为bean
     * @param json
     */
    private static SyncUser jsonToBean(String json,String tag){
        if (StringUtils.isEmpty(json)) return null;
        SyncUser syncUser = JSON.parseObject(json,SyncUser.class);
        log.info("转换成用户对象" + syncUser);

        return syncUser;

    }
}
