package me.tsinyong.monitor.client.listener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.tsinyong.monitor.client.codec.CustomerCodec;
import me.tsinyong.monitor.client.handler.ClientHandler;
import me.tsinyong.monitor.client.utils.ContentWriteToByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@ChannelHandler.Sharable
@Service
public class ClientConnectionWorker {

    @Autowired
    private CustomerCodec customerCodec;

    private SocketChannel socketChannel;

    public void sendMessageToServer(String msg) throws IOException {
        socketChannel.writeAndFlush(ContentWriteToByteBuf.writeToByteBuf(msg));
    }

    public void connectToServer() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                // 保持连接
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                // 绑定处理group
                .group(eventLoopGroup).remoteAddress("127.0.0.1", 2288)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 初始化编码器，解码器，处理器
                        socketChannel.pipeline().addLast(
                                customerCodec,
                                new ClientHandler());
                    }
                });
        // 进行连接
        ChannelFuture future;
        try {
            future = bootstrap.connect("127.0.0.1", 2288).sync();
            // 判断是否连接成功
            if (future.isSuccess()) {
                // 得到管道，便于通信
                socketChannel = (SocketChannel) future.channel();
                System.out.println("客户端开启成功...");
            } else {
                System.out.println("客户端开启失败...");
            }
            // 等待客户端链路关闭，就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
//            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅地退出，释放相关资源
//            eventLoopGroup.shutdownGracefully();
        }
    }
}
