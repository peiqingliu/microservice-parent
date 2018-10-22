package com.liupeiqing.rpc.rpctest;

import com.liupeiqing.rpc.customer.RPCClient;
import com.liupeiqing.rpc.provider.Calculator;
import com.liupeiqing.rpc.provider.CalculatorImpl;
import com.liupeiqing.rpc.registrycenter.RegistryServer;
import com.liupeiqing.rpc.registrycenter.RegistryServerImpl;

import java.io.IOException;

/**
 * @author liupeqing
 * @date 2018/10/19 15:51
 */
public class RPCTest {

    public static void main(String[] args) {

        //使用一个线程去注册服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RegistryServer server = new RegistryServerImpl(8090);
                    //注册
                    server.register(Calculator.class, CalculatorImpl.class);
                    //启动注册服务
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        //调用方去调用服务
        Calculator calculator = RPCClient.getClient(Calculator.class,"127.0.0.1",8090);
        int sun = calculator.add(1,2);
        System.out.println(sun);
    }
}
