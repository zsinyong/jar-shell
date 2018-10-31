package me.tsinyong.monitor.client.utils;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import me.tsinyong.monitor.client.protocol.JSMProtocol;

import java.io.IOException;

public class ContentWriteToByteBuf {

    //object -> String -> byte

    public static ByteBuf writeToByteBuf(JSMProtocol o) throws IOException {
        return writeToByteBuf(JSONObject.toJSONString(o));
    }

    public static ByteBuf writeToByteBuf(JSMProtocol o, ByteBuf b) throws IOException {
        return writeToByteBuf(JSONObject.toJSONString(o), b);
    }


    public static ByteBuf writeToByteBuf(String msg) throws IOException {
        ByteBuf byteBuf = Unpooled.buffer(100);
        return writeToByteBuf(msg, byteBuf);
    }

    public static ByteBuf writeToByteBuf(String msg, ByteBuf byteBuf) throws IOException {
        ByteBufOutputStream write = new ByteBufOutputStream(byteBuf);
        byte[] out = msg.getBytes(CharsetUtil.UTF_8);
        write.writeInt(out.length);
        write.write(out);
        write.flush();
        write.close();
        return byteBuf;
    }
}
