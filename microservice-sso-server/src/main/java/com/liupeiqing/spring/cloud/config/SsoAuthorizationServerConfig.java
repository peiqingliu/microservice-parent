package com.liupeiqing.spring.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author liupeiqing
 * @data 2018/9/3 20:50
 */
@Configuration
@EnableAuthorizationServer  //配置认证服务器
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 授权表达式
     * 配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置客户端
     * 客户端详情信息在这里进行初始化
     * 客户端详情信息可以存储在数据库，此处我就写了两个直接写死了
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.withClientDetails();
        clients.inMemory()
                .withClient("wuqianqian1")  ////客户端ID
                .secret("wuqianqiansecrect1")
                .authorizedGrantTypes("authorization_code","refresh_token")  // 设置验证方式 authorization_code：授权码类型。
                .scopes("all","read","write")
                .autoApprove(false)
                .accessTokenValiditySeconds(60)  //设置token过期时间
                .and()
                .withClient("wuqianqian2")
                .secret("wuqianqiansecrect2")
                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("all","read","write")
                // true 直接跳转到客户端页面，false 跳转到用户确认授权页面
                .autoApprove(true)
                .accessTokenValiditySeconds(60);  //设置token过期时间;
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    /*******************************************************/
    /**
     * JWT令牌（JWT Tokens）：
     * 使用JWT令牌你需要在授权服务中使用 JwtTokenStore，资源服务器也需要一个解码的Token令牌的类 JwtAccessTokenConverter，
     * JwtTokenStore依赖这个类来进行编码以及解码，因此你的授权服务以及资源服务都需要使用这个转换类。
     * Token令牌默认是有签名的，并且资源服务需要验证这个签名，因此呢，你需要使用一个对称的Key值，
     * 用来参与签名计算，这个Key值存在于授权服务以及资源服务之中。
     * 或者你可以使用非对称加密算法来对Token进行签名，Public Key公布在/oauth/token_key这个URL连接中，
     * 默认的访问安全规则是"denyAll()"，即在默认的情况下它是关闭的，
     * 你可以注入一个标准的 SpEL 表达式到 AuthorizationServerSecurityConfigurer 这个配置中来将它开启（例如使用"permitAll()"来开启可能比较合适，因为它是一个公共密钥）。
     *
     * 如果你要使用 JwtTokenStore，请务必把"spring-security-jwt"这个依赖加入到你的classpath中。
     *
     */


    /**官方文档
     TokenStore 的默认实现有三种：
     - InMemoryTokenStore
     - JdbcTokenStore
     - JwtTokenStore
     * 此处选择 第三种
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成JTW token
     * @return
     * 添加jwtSigningKey，以此生成秘钥，以此进行签名，只有jwtSigningKey才能获取信息。
     * 解码的Token令牌的类 JwtAccessTokenConverter，JwtTokenStore依赖这个类来进行编码以及解码，因此你的授权服务以及资源服务都需要使用这个转换类。
     * Token令牌默认是有签名的，并且资源服务需要验证这个签名，
     * 因此呢，你需要使用一个对称的Key值，用来参与签名计算，这个Key值存在于授权服务以及资源服务之中
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //秘签  一般写在配置文件中
        converter.setSigningKey("wuqianqian");
        return converter;
    }
}
