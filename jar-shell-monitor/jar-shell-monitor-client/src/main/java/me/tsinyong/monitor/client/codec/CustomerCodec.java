package me.tsinyong.monitor.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.CharsetUtil;
import me.tsinyong.monitor.client.protocol.JSMProtocol;
import me.tsinyong.monitor.client.utils.ContentWriteToByteBuf;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCodec extends ByteToMessageCodec<JSMProtocol> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        byteBuf.markReaderIndex();
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] content = new byte[length];
        byteBuf.readBytes(content);
        JSMProtocol jsmProtocol = new JSMProtocol(new String(content, CharsetUtil.UTF_8));
        list.add(jsmProtocol);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, JSMProtocol jsmProtocol, ByteBuf byteBuf) throws Exception {
        ContentWriteToByteBuf.writeToByteBuf(jsmProtocol);
    }
}
