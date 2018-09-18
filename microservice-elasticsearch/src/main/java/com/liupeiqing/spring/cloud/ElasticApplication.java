package com.liupeiqing.spring.cloud;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 使用elasticsearch进行全文检索
 */
@EnableElasticsearchRepositories(basePackages = {"elasticsearchRepository"})
@SpringBootApplication
public class ElasticApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ElasticApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
