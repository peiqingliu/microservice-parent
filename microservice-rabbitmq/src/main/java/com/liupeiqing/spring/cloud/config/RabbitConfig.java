package com.liupeiqing.spring.cloud.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 *
 * 带callback的消息发送
 *
 * 增加回调处理，这里不再使用application.properties默认配置的方式，会在程序中显示的使用文件中的配置信息。
 * 该示例中没有新建队列和exchange，用的是第5节中的topic.messages队列和exchange转发器。
 * 消费者也是第5节中的topicMessagesReceiver
 * @author liupeiqing
 * @data 2018/7/18 14:52
 */
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;  //虚拟主机

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;  //是否确认

    @Bean
    public ConnectionFactory connectionFactory(){

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses(addresses + ":" + port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        /** 如果要进行消息回调，则这里必须要设置为true */
        cachingConnectionFactory.setPublisherConfirms(publisherConfirms);
        return cachingConnectionFactory;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)   /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }
}
