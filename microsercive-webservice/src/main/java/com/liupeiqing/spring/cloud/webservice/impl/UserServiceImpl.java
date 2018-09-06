package com.liupeiqing.spring.cloud.webservice.impl;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.webservice.UserService;

import javax.jws.WebService;

/**
 * @author liupeiqing
 * @data 2018/8/20 15:06
 * endpointInterface 接口全路径
 *
 * targetNamespace:业务类所在的路径
 * endpointInterface :这是我的接口类所在路径；
 * 在CXF发布服务的时候，发布的是业务类(UserServiceImpl.java)，
 * 那么默认的命名空间就会是业务类所在包（路径），而对外界暴露的则是接口类(UserService.java)，
 * 那么对于客户端调用的时侯，需要按照接口类所在路径进行命名空间的定义。
 * 所以在发布之前我们要在业务类(UserServiceImpl.java)上增加注解，指定命名空间，然后再进行发布，
 */
@WebService(
        serviceName = "webUserServiceImpl",
        targetNamespace = "http://webservice.cloud.spring.liupeiqing.com",
        endpointInterface = "com.liupeiqing.spring.cloud.webservice.UserService")
public class UserServiceImpl implements UserService {

//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public User findUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("liupeiqing");
        user.setAge(20);
        return user;
    }

    @Override
    public String sayHello() {
        return "hello world";
    }
}
