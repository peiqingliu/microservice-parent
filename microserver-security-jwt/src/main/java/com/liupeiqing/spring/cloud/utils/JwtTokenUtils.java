package com.liupeiqing.spring.cloud.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liupeiqing.spring.cloud.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/16 15:44
 *
 * jwt工具类，对jjwt封装一下方便调用
 * JWT 的构成
 * 第一部分我们称它为头部（header）
 * header
 * JWT的头部承载的两部分信息：
 *
 * 声明类型，这里是jwt
 * 声明加密的算法，通常直接使用HMAC SHA256
 * 完整的头部就像下面这样的JSON
 *
 * {
 *      'typ':'JWT',
 *      'alg':'HS256'
 * }
 * 然后将头部进行base64加密（该加密是可以对称解密的），构成了第一部分
 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
 *
 * 第二部分我们称其为载荷（payload，类似于飞机上承载的物品）
 *
 *     载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分
 *
 * 标准中注册的声明
 * 公共的声明
 * 私有的声明
 *      标注中注册的声明（建议不强制使用）
 *
 * iss：jwt签发者
 * sub：jwt所面向的用户
 * aud：接收jwt的一方
 * exp：jwt的过期时间，这个过期时间必须大于签发时间
 * nbf：定义在什么时间之前，该jwt都是不可用的
 * iat：jwt的签发时间
 * jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
 *
 * 第三部分是签证（signature）
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";  //授权，批准

    public static final String TOKEN_PREFIX = "Bearer ";  //持票人

    public static final String SECRET = "jwtsecretdemo";

    public static final String ISS =  "echisan";  //iss: jwt签发者

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    //创建token
    public static String createToken(String username, Collection<? extends GrantedAuthority> authorities, boolean isRememberMe){
        long  expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,authorities);
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return token;
    }

    //从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    //从token中获取角色
    public static Collection<? extends  GrantedAuthority> getAuthoritiesFromToken(String token) {
        Collection<GrantedAuthority> authorities;
        try {
            Claims claims = getTokenBody(token);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixIn.class);
            // claims object is json, it needs to be deserialized
            //将map转换为Javabean
            JsonNode jsonNodeList = objectMapper.convertValue(claims.get(ROLE_CLAIMS), JsonNode.class);
            // json 转JavaBean
            //objectMapper.writeValueAsString(obj)  ---- >javaBean、列表数组转换为json字符串
            authorities = objectMapper.readValue(objectMapper.writeValueAsString(jsonNodeList), new TypeReference<Collection<SimpleGrantedAuthority>>(){});

        } catch (Exception e) {
            authorities = null;
        }
        return authorities;
    }

    //获得要求
    public static Claims getTokenBody(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
