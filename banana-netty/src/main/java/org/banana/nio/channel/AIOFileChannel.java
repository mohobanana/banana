package org.banana.nio.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AIOFileChannel {
    private static final Logger logger = LoggerFactory.getLogger(AIOFileChannel.class);

    public static void main(String[] args) throws IOException {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ)) {
            //1.ByteBuffer
            //2.读取的起始位置
            //3.附件
            //4.回调对象
            ByteBuffer buffer = ByteBuffer.allocate(16);
            logger.debug("start");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                //成功
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    logger.debug("completed");
                    attachment.flip();
                    logger.debug("msg:{}", Charset.defaultCharset().decode(attachment));
                }
                //失败
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            logger.debug("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.read();
    }

}
