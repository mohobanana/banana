package org.banana.nio.server;

import org.banana.nio.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BossServer {
    private static Logger logger = LoggerFactory.getLogger(BossServer.class);

    static class WorkerServer implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private boolean isStarted;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public WorkerServer(String name) {
            this.name = name;
        }

        //register与constructor分开：懒加载
        public String register(SocketChannel sc) throws IOException {
            if(!isStarted) {
                selector = Selector.open();
                thread = new Thread(this);
                thread.start();
                isStarted = true;
            }
            queue.add(()->{
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    sc.register(selector,SelectionKey.OP_READ,buffer);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();
            return name;
        }

        @Override
        public void run() {
            while(true){
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if(task!=null){
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        SocketChannel sc = (SocketChannel)key.channel();
                        try {
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            //正常断开 read返回为-1
                            int read = sc.read(buffer);
                            if(read == -1){
                                key.cancel();
                                logger.debug("disconnected:{}",sc);
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
//                            e.printStackTrace();
                            key.cancel();
                            logger.debug("disconnected:{}",sc);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel boss = ServerSocketChannel.open();
        boss.bind(new InetSocketAddress(8080));
        boss.configureBlocking(false);

        Selector bossSelector = Selector.open();
        boss.register(bossSelector, SelectionKey.OP_ACCEPT);

        WorkerServer[] workers = new WorkerServer[4];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new WorkerServer("worker"+i);
        }

        AtomicInteger count = new AtomicInteger(0);

        while(true){
            bossSelector.select();
            Iterator<SelectionKey> iterator = bossSelector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    logger.debug("connected:{}",sc);
                    String workerName = workers[count.getAndIncrement() % workers.length].register(sc);
                    logger.debug("registered:{}",workerName);
                }
            }
        }
    }

}
