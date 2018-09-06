package com.liupeiqing.spring.cloud.configUtil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/7/16 20:04
 */
@Component  //加上注释@Component，可以直接在其他地方使用@Autowired来创建其实例对象
@ConfigurationProperties
@PropertySource("classpath:config/remote.properties")  // 配置文件路径
public class MyWebConfig {
    private String uploadFilesUrl;
    private String uploadPicUrl;

    public String getUploadFilesUrl() {
        return uploadFilesUrl;
    }

    public void setUploadFilesUrl(String uploadFilesUrl) {
        this.uploadFilesUrl = uploadFilesUrl;
    }

    public String getUploadPicUrl() {
        return uploadPicUrl;
    }

    public void setUploadPicUrl(String uploadPicUrl) {
        this.uploadPicUrl = uploadPicUrl;
    }
}
