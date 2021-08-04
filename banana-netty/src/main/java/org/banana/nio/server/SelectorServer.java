package org.banana.nio.server;

import org.banana.nio.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class SelectorServer {
    private static Logger logger = LoggerFactory.getLogger(SelectorServer.class);

    public static void main(String[] args) throws IOException {
        //1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //2.创建selector
        Selector selector = Selector.open();
        //3.注册channel到selector上
        //事件发生后，通过SelectionKey得到事件，及发生在哪个channel
        //事件类型(accept|connect|read|write)
        SelectionKey sscKey = ssc.register(selector, 0, null);
        logger.debug("key:{}",sscKey);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            //select在事件未处理时不会阻塞，若不想处理，使用cancel()方法
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                //!处理key的时候要从selectedKeys中删除
                iter.remove();
                logger.debug("key:{}",key);
                if(key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    logger.debug("connected...:{}", sc);
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //正常断开 read返回为-1
                        int read = channel.read(buffer);
                        if(read == -1){
                            key.cancel();
                            continue;
                        }
                        BufferUtil.split(buffer);
                        if(buffer.limit()==buffer.position()){
                            logger.debug("double capacity:{}@{}->{}", buffer,buffer.capacity(),buffer.capacity()<<1);
                            ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()<<1);
                            buffer.flip();
                            newBuffer.put(buffer);
                            key.attach(newBuffer);
                        }
                    } catch (IOException e) {
                        //客户端异常断开需要捕获异常
                        e.printStackTrace();
                        key.cancel();
                    }
                }

            }

        }
    }
}
