//package com.liupeiqing.spring.cloud.config;
//
///**
// * @author liupeqing
// * @date 2018/9/17 14:40
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetAddress;
//
//@Slf4j
//@Configuration
//public class ElasticSearchConfig {
//
//    @Value("${spring.elasticsearch.host}")
//    private String host;  //elasticsearch 地址
//
//    @Value("${spring.elasticsearch.port}")
//    private Integer port;  //elasticsearch 端口
//
//    @Bean
//    public TransportClient client(){
//        TransportClient client = null;
//        try {
//            client = new PreBuiltTransportClient(Settings.EMPTY)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port));
//        }catch (Exception e){
//            log.error("创建elasticsearch客户端失败!");
//        }
//        log.info("创建elasticsearch成功!");
//        return client;
//    }
//}
