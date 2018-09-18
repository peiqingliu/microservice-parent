package com.liupeiqing.spring.cloud.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liupeiqing.spring.cloud.domain.JwtUser;
import com.liupeiqing.spring.cloud.domain.Role;
import com.liupeiqing.spring.cloud.moudle.LoginUser;
import com.liupeiqing.spring.cloud.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/16 17:01
 * 使用过shiro的人会知道,鉴权的话需要自己实现一个realm，
 * 重写两个方法，第一是用户验证，第二是鉴权
 *
 * 在spring-security中也不例外，这边需要实现两个过滤器。
 *
 * 使用JWTAuthenticationFilter去进行用户账号的验证，
 *
 * 使用JWTAuthorizationFilter去进行用户权限的验证。
 *
 * JWTAuthenticationFilter继承于UsernamePasswordAuthenticationFilter
 * 该拦截器用于获取用户登录的信息，
 * 只需创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证就可以了，
 * 不用自己查数据库再对比密码了，这一步交给spring去操作。
 * 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，验证的事情交给框架.
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  //  private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private ThreadLocal<Integer> rememberMe = new ThreadLocal<Integer>();

    private AuthenticationManager authenticationManager;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");  //登录
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从输入流中获取登录信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword(),new ArrayList<>());
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authResult){
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        log.info("jwtUser" + jwtUser.toString());
        System.out.print("jwtUser" + jwtUser.toString());
        boolean isrememberMe = rememberMe.get() == 1;

        Collection<? extends GrantedAuthority> authorities =  jwtUser.getAuthorities();
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(),authorities,isrememberMe);
        //        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setHeader("token",JwtTokenUtils.TOKEN_PREFIX + token);
    }

    //验证不成功
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,AuthenticationException failed) throws IOException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

}
