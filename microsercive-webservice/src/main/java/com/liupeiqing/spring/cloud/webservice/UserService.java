package com.liupeiqing.spring.cloud.webservice;

import com.liupeiqing.spring.cloud.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author liupeiqing
 * @data 2018/8/20 14:51
 */
@WebService
public interface UserService {

    @WebMethod
    User findUserById(@WebParam(name = "id") Long id);

    @WebMethod
    String sayHello();
}
