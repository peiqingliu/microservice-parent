package com.liupeiiqng.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 连接成功后，具体的通信代码
 * @author liupeqing
 * @date 2018/10/24 19:55
 */
@Slf4j
public class TcpClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 客户端与服务端连接后调用此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
        byte[] req = " CLIENT ---> REQUEST".getBytes();
        ByteBuf msg = Unpooled.buffer(req.length);
        msg.writeBytes(req);
        // 必须有flush
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String serverResponser = new String(req, "UTF-8");
        System.out.println("FROM SERVER IS : " + serverResponser);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("EXCEPTION : ", cause);
        ctx.close();
    }
}
