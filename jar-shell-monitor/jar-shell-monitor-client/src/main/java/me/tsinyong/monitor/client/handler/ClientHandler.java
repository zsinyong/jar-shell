package me.tsinyong.monitor.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.tsinyong.monitor.client.protocol.JSMProtocol;
import me.tsinyong.monitor.client.utils.ContentWriteToByteBuf;
import org.springframework.stereotype.Service;

@Service
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        JSMProtocol jsmProtocol = new JSMProtocol("Client Active");
        ctx.writeAndFlush(ContentWriteToByteBuf.writeToByteBuf(jsmProtocol));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client read " + msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client detect connection lose");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client read over");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
