package com.liupeiqing.rpc.customer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 客户端的远程代理对象
 * @author liupeqing
 * @date 2018/10/19 14:34
 */
public class RPCClient<T> {

    //泛型接口
    public static <T> T getClient(Class<T> clazz,final  String ip,final int port){
        //返回指定接口的代理类的实例，该接口将方法调用分派给指定的调用处理程序。
        // 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {

            //每个代理实例都有一个关联的调用处理程序。 当在代理实例上调用方法时，方法调用将被编码并分派到其调用处理程序的invoke方法。
            //处理代理实例上的方法调用并返回结果。
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectInputStream objectInputStream = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    // 2.创建Socket客户端，根据指定地址连接远程服务提供者
                    socket = new Socket(ip,port);
                    // 3.将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeUTF(clazz.getName());
                    objectOutputStream.writeUTF(method.getName());
                    objectOutputStream.writeObject(method.getParameterTypes());
                    objectOutputStream.writeObject(args);
                    // 4.同步阻塞等待服务器返回应答，获取应答后返回
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    return objectInputStream.readObject();  //返回应答内容

                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if (objectInputStream != null) objectInputStream.close();
                    if (objectOutputStream != null) objectOutputStream.close();
                    if (socket != null) socket.close();
                }
                return null;
            }
        });

    }
}
