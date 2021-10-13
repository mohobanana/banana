package org.banana.futurePromise;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.banana.netty.server.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * Desc: TestPromise
 * Created by mskj-mohao on 2021/9/24 3:42 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class TestPromise {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoop loop = group.next();
        DefaultPromise<Long> promise = new DefaultPromise<>(loop);

        new Thread(()->{
            logger.info("开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.setSuccess(System.currentTimeMillis());
        }).start();
        logger.info("等待");
        promise.addListener(new GenericFutureListener<Future<? super Long>>() {
            @Override
            public void operationComplete(Future<? super Long> future) throws Exception {
                logger.info(String.valueOf(future.get()));
            }
        });
        group.shutdownGracefully();
    }
}
