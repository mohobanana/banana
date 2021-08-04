package org.banana.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.banana.netty.server.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HelloClient {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    public static void main(String[] args) throws InterruptedException {
        //1.启动类
        new Bootstrap()
                //2.添加EventLoop
                .group(new NioEventLoopGroup())
                //3.选择服务器的channel实现
                .channel(NioSocketChannel.class)
                //4.定义操作行为
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //5.channel 初始化和客户端数据读写的通道，负责添加别的handler
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //6.添加handler
                        nioSocketChannel.pipeline().addLast(new StringEncoder()); //添加编码器
                    }
                })
                //6.连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync() //阻塞直到建立连接
                .channel()//连接对象
                //7.写入数据
                .writeAndFlush("hello world");
        logger.info("hello world");
    }
}
