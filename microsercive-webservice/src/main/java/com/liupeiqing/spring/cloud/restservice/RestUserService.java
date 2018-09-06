package com.liupeiqing.spring.cloud.restservice;

import com.liupeiqing.spring.cloud.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author liupeiqing
 * @data 2018/8/20 19:54
 *
 * Rest服务接口
 */
@Path("/user")
public interface RestUserService {

    @Path("/getStoreList")
    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    User findUserById(@QueryParam("id") Long id);

}
