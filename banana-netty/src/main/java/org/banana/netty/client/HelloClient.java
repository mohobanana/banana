package org.banana.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.banana.netty.server.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

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
                        //6.添加inboundHandler
                        nioSocketChannel.pipeline().addLast(new StringDecoder()); //将ByteBuf转为String
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() { //自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                logger.info(msg.toString());
                                super.channelRead(ctx, msg);
                            }
                        });
                        //6.添加handler
                        nioSocketChannel.pipeline().addLast(new LoggingHandler("INFO"));
                        nioSocketChannel.pipeline().addLast(new StringEncoder()); //添加编码器
                        nioSocketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                logger.info("sending msg:{}",msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                //6.连接到服务器
                .connect(new InetSocketAddress("localhost", 16000));
        Channel channel = channelFuture.channel();//连接对象
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                logger.info("good bye");
                group.shutdownGracefully();
            }
        });
        channelFuture.sync(); //阻塞直到建立连接
        logger.info("connected");

        Scanner scanner = new Scanner(System.in);
        channel.writeAndFlush(scanner.nextLine());//7.写入数据

//        channel.close();
    }
}
