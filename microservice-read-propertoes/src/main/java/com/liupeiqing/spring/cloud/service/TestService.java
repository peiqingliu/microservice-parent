package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.configUtil.MyWebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupeiqing
 * @data 2018/7/16 20:11
 */
@RestController
@EnableConfigurationProperties(MyWebConfig.class)
public class TestService {
    @Autowired
    MyWebConfig myWebConfig;

    public void test(){
        System.out.print("这是测试！");
        String str = myWebConfig.getUploadFilesUrl();
        System.out.print(str);
    }
}
