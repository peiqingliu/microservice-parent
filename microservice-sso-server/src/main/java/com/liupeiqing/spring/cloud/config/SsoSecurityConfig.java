package com.liupeiqing.spring.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author liupeiqing
 * @data 2018/9/4 14:42
 *
 * 认证服务器开放接口配置
 *
 * spring security 的配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("userDetailServiceImpl")  //同一个接口 有两个实现类 @Qualifier指定具体的实现类
    private UserDetailsService userDetailsService;

    // 加密方式
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用表单登录:指定了身份认证的方式
        http.formLogin()
                .loginPage("/authentication/require")  //自定义登录页面
                .loginProcessingUrl("/authentication/form")  //配置让Spring知道让UsernamePasswordAuthenticationFilter过滤器去处理/auth/form路径
                .and()
                .authorizeRequests()  //请求验证
                .antMatchers("/authentication/require",
                        "/authentication/form",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff2"
                )
                .permitAll()  //不需要验证
                .anyRequest()  //剩下所有的
                .authenticated()  //都要去验证
                .and()
                .csrf()
                .disable();  //默认会开启CSRF处理，判断请求是否携带了token，如果没有就拒绝访问。禁止掉

    }

    //跨域
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
