package com.liupeiiqng.netty.server;

import com.liupeiiqng.netty.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端
 * @author liupeqing
 * @date 2018/10/24 16:08
 */
@Slf4j
public class NettyServer {

    private static final int port = 6789;


    /**
     *无参构造创建的线程池的大小是CPU核心的两倍；
     * 初始化相应数量的线程放入线程池， 每个线程设置一个阻塞队列作为任务队列(LinkedBlockQueue)
     * @param port 端口号
     */
    public void bind(int port) {
        // 通过nio方式来接收连接和处理连接

        /***
         * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
         * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。
         * 在这个例子中我们实现了一个服务端的应用，
         * 因此会有2个NioEventLoopGroup会被使用。
         * 第一个经常被叫做‘boss’，用来接收进来的连接。
         * 第二个经常被叫做‘worker’，用来处理已经被接收的连接，
         * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
         * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
         * 并且可以通过构造函数来配置他们的关系。
         */
        /**
         * EventLoop：netty最核心的几大组件之一，就是我们常说的reactor，
         * 人为划分为boss reactor和worker reactor。
         * 通过EventLoopGroup（Bootstrap启动时会设置EventLoopGroup）生成，
         * 最常用的是nio的NioEventLoop，就如同EventLoop的名字，EventLoop内部有一个无限循环，
         * 维护了一个selector，处理所有注册到selector上的io操作，在这里实现了一个线程维护多条连接的工作。
         * ---------------------

         */
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{


            /**ServerBootstrap是监听服务端端口的启动器
             *
             * ServerBootstrap 是一个启动NIO服务的辅助启动类
             * 你可以在这个服务中直接使用Channel
             */
            ServerBootstrap b = new ServerBootstrap();

            /**
             * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not
             * set异常
             */
            b.group(boosGroup,workGroup);  // 注册线程池
            /***
             * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接
             * 这里告诉Channel如何获取新的连接.
             * ------------------------------------------------------
             * Channel：关联jdk原生socket的组件，常用的是NioServerSocketChannel和NioSocketChannel，
             * NioServerSocketChannel负责监听一个tcp端口，有连接进来通过boss reactor创建一个NioSocketChannel将其绑定到worker reactor，
             * 然后worker reactor负责这个NioSocketChannel的读写等io事件。
             */
            b.channel(NioServerSocketChannel.class);  //指定NIO的模式.NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP


            b.childHandler(new ChildChannelHandler());  // 绑定客户端连接时候触发操作 选择执行handler
            /***
             * 你可以设置这里指定的通道实现的配置参数。
             * 我们正在写一个TCP/IP的服务端，
             * 因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive。
             * 请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOptions的有一个大概的认识。
             */
            b.option(ChannelOption.SO_BACKLOG,128);
            /***
             * option()是提供给NioServerSocketChannel用来接收进来的连接。
             * childOption()是提供给由父管道ServerChannel接收到的连接，
             * 在这个例子中也是NioServerSocketChannel。
             */
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            log.info("SERVER-------> START");
            /***
             * 绑定端口并启动去接收进来的连接
             */
            ChannelFuture f = b.bind(port).sync();
            /**
             * 这里会一直等待，直到socket被关闭  关闭服务器通道
             */
            f.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("服务端绑定错误!" + e.getMessage());
        }finally {
            /**
             * 优雅的关闭  释放线程池资源
             */

            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();

        }


    }

    /***
     * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
     * 他的目的是帮助使用者配置一个新的Channel。
     * 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
     * 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
     * 然后提取这些匿名类到最顶层的类上。
     */
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new DiscardServerHandler());
        }
    }


    public static void main(String[] args) {

        new NettyServer().bind(port);

    }
}
