package org.banana.netty.nonBlocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        //nio非阻塞模式，单线程
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        //3.连接集合
        List<SocketChannel> channelList = new ArrayList<>();
        while (true) {
//            logger.debug("connecting...");
            SocketChannel sc = ssc.accept();//非阻塞模式
            if (sc != null) {
                logger.debug("connected...{}", sc);
                sc.configureBlocking(false);
                channelList.add(sc);
            }
            for (SocketChannel channel : channelList) {
                int read = channel.read(byteBuffer);//非阻塞模式，没读到数据返回0
                if (read > 0) {
                    logger.debug("before read...{}", channel);
                    byteBuffer.flip();//读模式
                    String message = Charset.defaultCharset().decode(byteBuffer).toString();
                    logger.debug("message:{}", message);
                    byteBuffer.clear();//写模式
                    logger.debug("after read...{}", channel);
                }
            }
        }
    }
}
