package org.banana.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {
    private static Logger logger = LoggerFactory.getLogger(BlockingServer.class);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                logger.info("key:{}",key);
                iter.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector,0);
                    //向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 3000000; i++) {
                        sb.append("0");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    //返回实际写入的字节数
                    int write = sc.write(buffer);
                    logger.info("write:{}",write);
                    //若有剩余则关注可写事件
                    if(buffer.remaining()>0) {
                        scKey.interestOps(SelectionKey.OP_WRITE);
                    }
                    scKey.attach(buffer);
                } else if(key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    if(buffer.remaining()<=0){
                        //若写完了，清除操作
                        key.attach(null);
                        key.interestOps(key.interestOps()-SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
