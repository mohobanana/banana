package org.banana.util;

import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;

public class BoundTestUtil {

    public static void createTestChannel(ChannelHandler... channelHandlers){
        new EmbeddedChannel(channelHandlers);
    }
}
