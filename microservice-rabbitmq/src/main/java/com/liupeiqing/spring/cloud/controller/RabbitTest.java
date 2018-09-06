package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.HelloReceiver.TopReservicer;
import com.liupeiqing.spring.cloud.callback.CallBackSender;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.hellosend1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author liupeiqing
 * @data 2018/7/17 20:19
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitTest {

    @Autowired
    private HelloSender1 helloSender1;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired
    private UserSend userSend;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private CallBackSender callBackSender;

    @Autowired
    private User user;

    @Autowired

    /**
     * post接收数据，只能以body的形式接受  一对一测试
     */
    @PostMapping(value = "/hello",produces = MediaType.APPLICATION_JSON_VALUE)
    public void oneToOne(@RequestBody User user){
        helloSender1.send(user.getUserName());
    }

    /**
     * 从以上结果可知，生产者发送的10条消息，分别被两个消费者接收了,是平均接受各接受5条
     * @param user
     */
    @PostMapping(value = "/oneToMang",produces = MediaType.APPLICATION_JSON_VALUE)
    public void oneToMang(@RequestBody User user){
        for (Integer i =0;i<10;i++){
            helloSender1.send(user.getUserName()+user.getPassWord());
        }

    }

    /**
     * 和一对多一样，接收端仍然会均匀接收到消息
     * @param user
     */
    @PostMapping("/mangToMang")
    public void  mangToMang(@RequestBody User user){
        for(int i=0;i<10;i++){
            this.helloSender1.send(user.getUserName());
            this.helloSender2.send(user.getPassWord());
        }
    }


    @PostMapping(value = "/sendUser")
    public void sendUser(@RequestBody User user){
        this.userSend.send(user);
    }


    /**
     * 由以上结果可知：sender1发送的消息,routing_key是“topic.message”，
     * 所以exchange里面的绑定的binding_key是“topic.message”，topic.＃都符合路由规则;
     * 所以sender1 发送的消息，两个队列都能接收到；
     *
     * sender2发送的消息，routing_key是“topic.messages”，所以exchange里面的绑定的binding_key只有topic.＃都符合路由规则;所以sender2发送的消息只有队列
     *
     * topic.messages能收到。
     */
    @PostMapping(value = "/topicSend")
    public void topicSend(){
        this.topicSender.sendTopic();
    }


    @PostMapping(value = "/topicSend2")
    public void topicSend2(){
        this.topicSender.sendTopic2();
    }

    /**
     * 有结果可知 就算fanoutSender发送消息的时候，指定了routing_key为"abcd.ee"，但是所有接收者都接受到了消息
     */
    @PostMapping(value = "/fanoutTest")
    public void fanoutTest(){

        this.fanoutSender.send();
    }

    @PostMapping("/callback")
    public void callbak() {
        callBackSender.send();
    }
}
