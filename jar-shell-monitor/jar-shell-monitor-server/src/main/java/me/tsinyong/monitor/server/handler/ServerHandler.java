package me.tsinyong.monitor.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.tsinyong.monitor.server.protocol.JSMProtocol;
import me.tsinyong.monitor.server.utils.ContentWriteToByteBuf;
import org.springframework.stereotype.Service;

@ChannelHandler.Sharable
@Service
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        JSMProtocol jsmProtocol = (JSMProtocol) msg;
        System.out.println("Server read :=" + msg);
        jsmProtocol = new JSMProtocol("Done" + System.currentTimeMillis());
        ctx.channel().writeAndFlush(ContentWriteToByteBuf.writeToByteBuf(jsmProtocol));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server read over");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server detect connection lose");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

}
