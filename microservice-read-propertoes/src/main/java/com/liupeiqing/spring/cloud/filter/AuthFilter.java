package com.liupeiqing.spring.cloud.filter;

import com.liupeiqing.spring.cloud.configUtil.MyWebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author liupeiqing
 * @data 2018/7/16 21:00
 */
@Order(1)
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*html")
public class AuthFilter implements Filter {

    @Autowired
    private MyWebConfig myWebConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("访问路径权限过滤器初始化");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("访问路径权限开始执行过滤器");
    }

    @Override
    public void destroy() {
        System.out.println("访问路径权限过滤器销毁");

    }
}
