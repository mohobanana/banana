package org.banana.netty.client;

import org.banana.netty.server.BlockingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteClient {
    private static Logger logger = LoggerFactory.getLogger(BlockingServer.class);

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        int count = 0;
        while(true){
            count+=sc.read(buffer);
            logger.info("count:{}",count);
            buffer.clear();
        }

    }
}
