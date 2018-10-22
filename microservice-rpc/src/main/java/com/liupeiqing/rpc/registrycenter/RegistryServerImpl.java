package com.liupeiqing.rpc.registrycenter;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author liupeqing
 * @date 2018/10/19 11:39
 */
@Slf4j
public class RegistryServerImpl implements RegistryServer {

    //private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static int coreThread = Runtime.getRuntime().availableProcessors();
    //LinkedBlockingQueue 阻塞的线程安全的队列
    private static ExecutorService executorService =
            new ThreadPoolExecutor(coreThread,coreThread,0l,TimeUnit.MICROSECONDS,new LinkedBlockingQueue<Runnable>());

    //用于存放注册的class
    private Map<String,Class> serverRegistry = new HashMap<String,Class>();

    private static boolean isRunning = Boolean.FALSE;

    private static int port;

    public RegistryServerImpl(int port){

        this.port = port;
    }

    @Override
    public void stop() {
        isRunning = false;
        //关闭链接池
        executorService.shutdown();
    }

    @Override
    public void start(){

        try {
        ServerSocket serverSocket = new ServerSocket();
        //监听某个端口
        serverSocket.bind(new InetSocketAddress(port));
        log.info("服务端已经启动，正在等待连接");

            while (true){  //一直监听
                //1.监听客户端链接，如果有链接，则封装在线程类里面
                executorService.execute(new ServiceTask(serverSocket.accept()));
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }

    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        serverRegistry.put(serviceInterface.getName(),impl);

    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }

    //内部类用来出来调用服务的任务
    private  class ServiceTask implements Runnable{

        Socket socket = null;

        public ServiceTask(Socket client){
            this.socket = client;
        }
        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {

                // 2将客户端发送的码流 反序列化成对象，反射调用服务的 接口实现类，获取执行结果
                input = new ObjectInputStream(socket.getInputStream());
                String serviceName = input.readUTF();  //获取服务的接口名
                String methodName = input.readUTF();  //获取服务的接口方法
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();  //获取接口中参数的类型
                Object[] arguments = (Object[]) input.readObject();
                Class serverClass = serverRegistry.get(serviceName);
                if (null == serverClass){
                    throw new ClassNotFoundException("serverClass" + serviceName);
                }
                Method methods = serverClass.getMethod(methodName,parameterTypes);
                Object result = methods.invoke(serverClass.newInstance(),arguments);

                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(result);
            }catch (IOException e){
                e.printStackTrace();
                log.error("IO流出现异常" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                //关闭流
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
