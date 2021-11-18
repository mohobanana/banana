package org.banana.nio.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static io.netty.util.internal.StringUtil.NEWLINE;

public class BufferUtil {
    private static Logger logger = LoggerFactory.getLogger(BufferUtil.class);

    public static void split(ByteBuffer source){
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                target.flip();
                String message = Charset.defaultCharset().decode(target).toString();
                logger.debug("msg:{}", message);
            }
        }
        source.compact();
    }

    public static String getByteBufInfo(ByteBuf byteBuf){
        int length = byteBuf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder sb = new StringBuilder(rows*80*2)
                .append("reader index:").append(byteBuf.readerIndex())
                .append("|writer index:").append(byteBuf.writerIndex())
                .append("|capacity:").append(byteBuf.capacity())
                .append(NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb,byteBuf);
        return sb.toString();
    }
}
