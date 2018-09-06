package com.liupeiqing.spring.cloud.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


/**
 * @author liupeiqing
 * @data 2018/8/20 19:39
 */
public class WebClient {

    public static void main(String[] args) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://127.0.0.1:1019/ws/userWService?wsdl");
        Object[] objects  = client.invoke("findUserById",20);
        System.out.println("*****"+objects[0].toString());
    }

}
