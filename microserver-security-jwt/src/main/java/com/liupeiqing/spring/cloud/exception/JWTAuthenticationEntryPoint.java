package com.liupeiqing.spring.cloud.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liupeiqing
 * @data 2018/8/16 19:22
 * 自定义验证exception
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reson = "统一处理,原因：" + authException.getMessage();
        response.getWriter().write(new ObjectMapper().writeValueAsString(reson));
    }
}
