package org.banana.futurePromise;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.banana.netty.server.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Desc: TestNettyFuture
 * Created by mskj-mohao on 2021/9/24 3:37 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class TestNettyFuture {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup groups = new NioEventLoopGroup();
        EventLoop next = groups.next();
        Future<Long> fu = next.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return System.currentTimeMillis();
            }
        });
        //同步
//        logger.info(String.valueOf(fu.get()));
        //异步
        fu.addListener(new GenericFutureListener<Future<? super Long>>() {
            @Override
            public void operationComplete(Future<? super Long> future) throws Exception {
                logger.info(String.valueOf(fu.getNow()));
            }
        });
        groups.shutdownGracefully();
    }
}
