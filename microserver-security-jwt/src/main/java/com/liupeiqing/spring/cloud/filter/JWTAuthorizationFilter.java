package com.liupeiqing.spring.cloud.filter;

import com.liupeiqing.spring.cloud.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/16 18:36
 *
 * 验证成功当然就是进行鉴权了，每一次需要权限的请求都需要检查该用户是否有该权限去操作该资源，
 * 当然这也是框架帮我们做的，那么我们需要做什么呢？
 * 很简单，只要告诉spring-security该用户是否已登录，是什么角色，拥有什么权限就可以了。
 * JWTAuthenticationFilter继承于BasicAuthenticationFilter，至于为什么要继承这个我也不太清楚了，
 * 这个我也是网上看到的其中一种实现，实在springSecurity苦手，
 * 不过我觉得不继承这个也没事呢（实现以下filter接口或者继承其他filter实现子类也可以吧）只要确保过滤器的顺序，
 * JWTAuthorizationFilter在JWTAuthenticationFilter后面就没问题了
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

   // private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        //如果请求头中没有Authorization信息则直接放行了
        if ("" .equals(tokenHeader)|| null ==tokenHeader || tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        // 如果请求头中有token，则进行解析，并且设置认证信息
        //通过调用 SecurityContextHolder.getContext().setAuthentication(...) 建立安全上下文的实例，传递到返回的身份认证对象上
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request,response,chain);
    }

     //将该令牌传递给 AuthenticationManager 实例进行验证,验证成功后，AuthenticationManager 会返回填充好的 Authentication 实例
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        String token  = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        log.info("token" + token);
        String username = JwtTokenUtils.getUsername(token);
        Collection<? extends  GrantedAuthority> roles = JwtTokenUtils.getAuthoritiesFromToken(token);

        if (null != username){
            return new UsernamePasswordAuthenticationToken(username,null,roles);
        }
        return null;
    }
}
