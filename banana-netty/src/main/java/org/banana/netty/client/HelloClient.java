package org.banana.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.banana.netty.server.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HelloClient {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        //1.启动类
        ChannelFuture channelFuture = new Bootstrap()
                //2.添加EventLoop
                .group(group)
                //3.选择服务器的channel实现
                .channel(NioSocketChannel.class)
                //4.定义操作行为
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //5.channel 初始化和客户端数据读写的通道，负责添加别的handler
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //6.添加handler
                        nioSocketChannel.pipeline().addLast(new LoggingHandler("INFO"));
                        nioSocketChannel.pipeline().addLast(new StringEncoder()); //添加编码器
                    }
                })
                //6.连接到服务器
                .connect(new InetSocketAddress("localhost", 8080));
        channelFuture.sync(); //阻塞直到建立连接

        Channel channel = channelFuture.channel();//连接对象
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                logger.info("good bye");
                group.shutdownGracefully();
            }
        });
        channel.writeAndFlush("hello world");//7.写入数据
        channel.close();
    }
}
