package com.liupeiiqng.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 *
 *
 *  * 服务端处理通道.
 *  * ResponseServerHandler 继承自 ChannelHandlerAdapter，
 *  * 这个类实现了ChannelHandler接口，
 *  * ChannelHandler提供了许多事件处理的接口方法，
 *  * 然后你可以覆盖这些方法。
 *  * 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 *  * 用来对请求响应
 *  *
 * @author liupeqing
 * @date 2018/11/2 17:01
 */
public class ResponseServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 每当从客户端接受到请求的数据，这个方法收到消息是会被调用
     * 此处我们调用write(Object)方法把接受到的请求一个一个写入缓冲区
     * @param ctx 通道处理的上下问
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in  = (ByteBuf) msg;
        System.out.println("这是接收到的数据" + in.toString(CharsetUtil.UTF_8));
        Object str = "这是返回的数据----》";
        ctx.write(msg);  //该方法是写入缓冲区
        //cxt.writeAndFlush(msg)
        //请注意，这里我并不需要显式的释放，因为在进入的时候netty已经自动释放
        // ReferenceCountUtil.release(msg);
    }

    /**
     * ctx.write()方法不会直接将数据写入管道内，是先写入缓冲区，然后
     * 需要调用ctx.flush()方法来把缓冲区中数据强行输出。
     * 或者你可以在channelRead方法中用更简洁的cxt.writeAndFlush(msg)以达到同样的目的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

    }

    /**
     * 该方法异常时候触发
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /***
         * 发生异常后，关闭连接
         */
        cause.printStackTrace();
    }
}
