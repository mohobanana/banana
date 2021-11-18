package org.banana.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.banana.nio.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc: TestByteBuf
 * Created by mskj-mohao on 2021/11/17 2:37 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class TestByteBuf {
    private static final Logger logger = LoggerFactory.getLogger(TestByteBuf.class);

    public static void main(String[] args) {
        /**
         * 池化：针对创建消耗大的资源，重用byteBuf，节约内存
         * 堆内存：分配效率高，读写效率低，需要受到GC影响
         * 直接内存：使用系统内存，分配效率低，读写效率高，减少一次内存复制（netty默认）
         */
        //创建池化基于堆的ByteBuf
        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();//默认大小为256，可动态扩容,nio中的byteBuffer不可变容
        //创建池化基于直接内存的ByteBuf
        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();//默认大小为256，可动态扩容,nio中的byteBuffer不可变容
        heapBuffer.writeBytes("abcedfghijklmn".getBytes());
        logger.info(BufferUtil.getByteBufInfo(heapBuffer));

    }
}
