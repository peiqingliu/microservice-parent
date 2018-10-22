package com.liupeiqing.rpc.registrycenter;

import java.io.IOException;

/**
 * @author liupeqing
 * @date 2018/10/19 11:35
 */
public interface RegistryServer {

    //停止
    public void stop();
    //启动
    public void start() throws IOException;
    //将服务端的服务注册
    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    //获取端口
    public int getPort();
}
