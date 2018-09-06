package com.liupeiqing.spring.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author liupeiqing
 * @data 2018/9/4 14:42
 *
 * 认证服务器开放接口配置
 *
 * spring security 的配置文件
 */
@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {
}
