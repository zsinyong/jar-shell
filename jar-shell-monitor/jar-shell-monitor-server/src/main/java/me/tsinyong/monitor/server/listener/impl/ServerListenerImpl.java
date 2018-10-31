package me.tsinyong.monitor.server.listener.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.tsinyong.monitor.server.codec.CustomerCodec;
import me.tsinyong.monitor.server.handler.ServerHandler;
import me.tsinyong.monitor.server.listener.IServerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ChannelHandler.Sharable
@Service
public class ServerListenerImpl implements IServerListener {

    private ServerSocketChannel serverSocketChannel;

    @Autowired
    private ServerHandler serverHandler;

    @Autowired
    private CustomerCodec customerCodec;

    @Override
    public void startServer() {
        //服务端要建立两个group，一个负责接收客户端的连接，一个负责处理数据传输
        //连接处理group
        EventLoopGroup boss = new NioEventLoopGroup();
        //事件处理group
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 绑定处理group
        bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                //保持连接数
                .option(ChannelOption.SO_BACKLOG, 128)
                //有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                //保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //处理新连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        // 增加任务处理
                        ChannelPipeline p = sc.pipeline();
                        p.addLast(customerCodec, serverHandler);
                    }
                });
        ChannelFuture future;
        try {
            future = bootstrap.bind(2288).sync();
            if (future.isSuccess()) {
                serverSocketChannel = (ServerSocketChannel) future.channel();
                System.out.println("服务端开启成功");
            } else {
                System.out.println("服务端开启失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}
