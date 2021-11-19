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
         * VM Options:-Dio.netty.allocator.type={unpooled|pooled(默认)}
         * 堆内存：分配效率高，读写效率低，需要受到GC影响
         * 直接内存：使用系统内存，分配效率低，读写效率高，减少一次内存复制（netty默认）
         */
        //创建池化基于直接内存的ByteBuf
        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();//默认大小为256，可动态扩容,nio中的byteBuffer不可变容
        //创建池化基于堆的ByteBuf
        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();//默认大小为256，可动态扩容,nio中的byteBuffer不可变容
        directBuffer.writeBytes(new byte[]{1,2,3,4});
        directBuffer.writeInt(5);//大端写入，网络通信默认格式
        //directBuffer.writeIntLE(5);//小端写入
        logger.info(BufferUtil.getByteBufInfo(directBuffer));
        System.out.println(directBuffer.readByte());
        System.out.println(directBuffer.readByte());
        System.out.println(directBuffer.readByte());
        System.out.println(directBuffer.readByte());
        logger.info("=============================after read=============================");
        logger.info(BufferUtil.getByteBufInfo(directBuffer));

        /**
         * 重复读，使用mark/reset，或者直接使用get/set方法
         */
        logger.info("===========================mark read index===========================");
        directBuffer.markReaderIndex();
        System.out.println(directBuffer.readInt());
        logger.info(BufferUtil.getByteBufInfo(directBuffer));
        directBuffer.resetReaderIndex();
        logger.info("=============================after reset=============================");
        logger.info(BufferUtil.getByteBufInfo(directBuffer));

        /**
         * 内存释放
         * 非池化实现：   UnpooledHeapByteBuf:使用jvm内存，等待GC回收内存
         *              UnpooledDirectByteBuf:使用直接内存，需要特殊方法回收内存
         * 池化实现：    PooledByteBuf:需要更复杂的规则回收内存
         *
         * netty使用引用计数法来控制内存回收，每个ByteBuf接口都实现了ReferenceCounted接口
         *      每个ByteBuf对象的初始计数为1
         */




    }
}
