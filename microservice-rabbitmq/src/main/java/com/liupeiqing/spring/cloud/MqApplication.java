package com.liupeiqing.spring.cloud;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author liupeiqing
 * @data 2018/7/17 19:07
 */
@SpringBootApplication
public class MqApplication {

    final static String queueName = "hello";

    /**
     * 通常我们谈到队列服务, 会有三个概念： 发消息者、队列、收消息者，RabbitMQ 在这个基本概念之上, 多做了一层抽象,
     * 在发消息者和 队列之间, 加入了交换器 (Exchange).
     * 这样发消息者和队列就没有直接联系, 转而变成发消息者把消息给交换器, 交换器根据调度策略再把消息再给队列。
     */

    /**
     * 生产一个名称为hello的queue
     * @return
     */
    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

    @Bean
    public Queue userQueue(){
        return new Queue("user");
    }

    /**
     * 交换机exchange
     *
     * Direct：direct 类型的行为是"先匹配, 再投送". 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
     * Topic：按规则转发消息（最灵活）
     * Headers：设置header attribute参数类型的交换机
     * Fanout：转发消息到所有绑定队列
     */

    /****************以下是验证toppic exchange的队列*********************/
    //消费者各自监控自己的队列；交换机通过一种模式策略确定生产者的消息放入那个队列。
    @Bean
    public Queue queueMessage1(){
        return new Queue("message");
    }

    @Bean
    public Queue queueMessage2(){
        return new Queue("messages");
    }

    /*************************************/
    //===============以下是验证Fanout Exchange的队列==========
    //Fanout Exchange 消息广播的模式，不管路由键或者是路由模式，会把消息发给绑定给它的全部队列，如果配置了routing_key会被忽略。
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     *
     * queueMessage只匹配"topic.message"队列
     * @param queueMessage1
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage1, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessage1).to(topicExchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * 为什么是队列topic.messages和exchange绑定？？？？
     *
     * 如果  我们消息 的路由设置 为 topic.message，那么符合第一和第二个binding  所以两个都会就收到
     * 如果 我们消息设置为topic.messages 那么只符合第二个binding，第一个不符合，因此 只有第二个会接受到。
     * 同时匹配两个队列
     * @param queueMessage2 这个参数 是从容器中，我们根据名字去取的，如果换成其他的  就会找不到
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessage2, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage2).to(exchange).with("topic.#");
    }

    /**
     * 消息广播的模式 不用设置路由键
     * Fanout 就是我们熟悉的广播模式或者订阅模式
     * @param AMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

    public static void main(String[] args){
        SpringApplication.run(MqApplication.class,args);
    }

}
