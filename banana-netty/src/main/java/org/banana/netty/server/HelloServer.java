package org.banana.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.banana.nio.server.SelectorServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class HelloServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    public static void main(String[] args) throws IOException {
        //1.启动器 负责组装netty组件，启动服务器
        ChannelFuture channelFuture = new ServerBootstrap()
                //2.BossEventLoop(BossServer) WorkerEventLoop(WorkerServer(selector,thread)
                .group(new NioEventLoopGroup())
                //3.选择ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4.boss负责处理连接 worker(child) 负责读写，下列代码定义了worker的操作行为
                .childHandler(
                        //5.channel 初始化和客户端数据读写的通道，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                //6.添加handler
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
                //7.绑定监听端口
                .bind(16000);
        Channel channel = channelFuture.channel();
        Scanner scanner = new Scanner(System.in);
        channel.write(scanner.nextLine());
    }
}
