package com.yzb.andong.config.server.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HeartbeatHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

//    @Override
//    protected void channelRead0(ChannelHandlerContext context, ByteBuf byteBuf) throws Exception {
//
//    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf buf);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:// 要处理读 by Alex
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.error(ctx.channel().id().asLongText() + "---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.error(ctx.channel().id().asLongText() + "---" + ctx.channel().remoteAddress() + " is inactive---");
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        LOGGER.error(ctx.channel().id().asLongText() + "---READER_IDLE---close");
        //心跳重新恢复
        try {
            if (ctx.channel().isActive()) {
                removeChannel(ctx);
                LOGGER.error("=channel 移除==");
                ctx.close();
            } else {
                LOGGER.error("channel 无效，重置");
                removeChannel(ctx);
                ctx.close();
            }
        } catch (Exception e) {
            LOGGER.error("心跳异常：" + ExceptionUtils.getStackTrace(e));
            removeChannel(ctx);
            ctx.close();
        }

    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        LOGGER.error(ctx.channel().id().asLongText() + "---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }

    protected abstract void removeChannel(ChannelHandlerContext ctx);

    protected abstract void replayHeartChannel(ChannelHandlerContext ctx);
}