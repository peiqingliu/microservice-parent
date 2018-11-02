package com.liupeiiqng.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liupeqing
 * @date 2018/10/24 18:50
 */
/**
 * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应
 * DiscardServerHandler 继承自 ChannelHandlerAdapter，
 * 这个类实现了ChannelHandler接口，
 * ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。
 * 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 *
 */

/**
 * ChannelHandler有两个子类ChannelInboundHandler和ChannelOutboundHandler，
 * 这两个类对应了两个数据流向，如果数据是从外部流入我们的应用程序，我们就看做是inbound，相反便是outbound。
 * 其实ChannelHandler和Servlet有些类似，一个ChannelHandler处理完接收到的数据会传给下一个Handler，或者什么不处理，直接传递给下一个。
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 这里我们覆盖了channelRead方法，
     * 每当从客户端收到新的数据时，
     * 这个方法会在收到消息时被调用
     * 这个例子中，收到的消息的类型是ByteBuf
     * @param ctx  通道处理的上下文信息
     * @param msg  接收的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try{
            ByteBuf in = (ByteBuf) msg;
            //打印输入的字符
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        }catch (Exception e){
            log.error("读取客户端信息失败!" + e.getMessage());
        }finally {
            /**
             * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
             * 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
             */
            ReferenceCountUtil.release(msg);

        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final String sessionId = ctx.channel().id().asLongText();
        log.debug("终端断开连接:{}");
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.error("服务器主动断开连接:{}");
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常:{}", cause);
        //发生异常,关闭链接
        cause.printStackTrace();
    }
}
