package com.liupeiqing.spring.cloud.config;

import com.liupeiqing.spring.cloud.exception.JWTAuthenticationEntryPoint;
import com.liupeiqing.spring.cloud.filter.JWTAuthenticationFilter;
import com.liupeiqing.spring.cloud.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author liupeiqing
 * @data 2018/8/16 18:55
 *
 * 需要开启一下注解@EnableWebSecurity
 * Spring Security默认是禁用注解的，要想开启注解，
 * 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
 * 来判断用户对某个控制层的方法是否具有访问权限
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")  //同一个接口 有两个实现类 @Qualifier指定具体的实现类
    private UserDetailsService userDetailsService;

    // 加密密码的，安全第一嘛~
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 设置请求校验规则
     * .antMatchers("/tasks/**") 用来匹配请求的路径
     * .authenticated() 是需要验证
     * .permitAll() 是直接放行  例如 我这边配置了全部放行 所有的请求  直接到controller层了 在方法上如果没有加权限的规则   就会直接访问过去
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 测试用资源，需要验证了的用户才能访问   注册不需要
                .antMatchers("/auth/register")
                .permitAll()
//                .antMatchers(HttpMethod.DELETE,"/tasks/**").hasRole("ADMIN")
                //其他的都放行   都需要验证
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                //不要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
    }

    //跨域
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
