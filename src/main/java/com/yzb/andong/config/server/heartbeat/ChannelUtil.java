package com.yzb.andong.config.server.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelUtil {

    private static Logger logger = LoggerFactory.getLogger(ChannelUtil.class);

    public static byte[] writeMsgSync(byte[] msg, Channel channel, int timeout) throws Exception {
        if (channel.isActive()) {
            synchronized (channel) {
                Attribute<byte[]> attr = channel.attr(AttributeKey.valueOf(channel.id().asLongText()));
                synchronized (attr) {
                    attr.set(msg);
                    logger.info("发消息:" + ProtocolUtils.readByteToHex(msg));
                    ByteBuf buf = channel.alloc().buffer();
                    buf.writeBytes(msg);
                    channel.writeAndFlush(buf);
                    attr.wait(timeout * 1000);
                    byte[] ret = attr.get();
                    logger.info("收消息:" + ProtocolUtils.readByteToHex(ret) + ret.getClass());
                    attr.set(null);
                    if (ret != msg) {
                        return ret;
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}