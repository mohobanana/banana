package org.banana.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class BlockingServer {
    private static Logger logger = LoggerFactory.getLogger(BlockingServer.class);
    public static void main(String[] args) throws IOException {
        //nio阻塞模式，单线程
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        //3.连接集合
        List<SocketChannel> channelList = new ArrayList<>();
        while(true){
            logger.debug("connecting...");
            SocketChannel sc = ssc.accept();//阻塞方法，线程停止运行
            logger.debug("connected...{}", sc);
            channelList.add(sc);
            for (SocketChannel channel : channelList) {
                logger.debug("before read...{}",channel);
                channel.read(byteBuffer);//阻塞方法，线程停止运行
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
                String message = new String(bytes);
                logger.debug("message:{}",message);
                byteBuffer.clear();
                logger.debug("after read...{}",channel);
            }
        }
    }
}
