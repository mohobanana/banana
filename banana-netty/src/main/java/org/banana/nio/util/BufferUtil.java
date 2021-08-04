package org.banana.nio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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
}
