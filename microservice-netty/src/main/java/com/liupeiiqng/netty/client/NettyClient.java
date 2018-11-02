package com.liupeiiqng.netty.client;

import com.liupeiiqng.netty.handler.TcpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyClient  Netty客户端
 * @author liupeqing
 * @date 2018/10/24 19:45
 */
@Slf4j
public class NettyClient {

    /**
     * 用做链接
     * @param port
     * @param ip
     */
    private void connect(int port,String ip){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(group);
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer(){
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new TcpClientHandler());

                }
            });
            ChannelFuture cf = bootstrap.connect(ip,port).sync();
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("客户端链接出错" + e.getMessage());
        }finally {
            try {
                group.shutdownGracefully().sync(); // 释放线程池资源
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new NettyClient().connect(6789,"127.0.0.1");
    }
}
