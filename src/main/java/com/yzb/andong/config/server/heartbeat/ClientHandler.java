package com.yzb.andong.config.server.heartbeat;//package com.yzb.andong.config.server.heartbeat;
//
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.AUTH_RESPONSE_HEAD;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.OK;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.PWT;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.RANDOM_SIZE;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.REQUIRE_AUTH_HEAD;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.readByteToHex;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//
//public class ClientHandler extends HeartbeatHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);
//
//    @Override
//    protected void handleData(ChannelHandlerContext ctx, ByteBuf byteBuf) {
//        byte[] data = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(data);
//        LOGGER.info("receive data:" + readByteToHex(data));
//        // heartbeat request
//        if (data.length == OK.length && Arrays.equals(data, OK)) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            replyHeartBeat(ctx);
//            // authorization request
//        } else if (data.length == REQUIRE_AUTH_HEAD.length + RANDOM_SIZE) {
//            replyAuthRequire(ctx, data);
//        } else {
//            // error input
//            ctx.writeAndFlush("invalid resp");
//            ctx.close();
//        }
//    }
//
//    @Override
//    protected void handleAllIdle(ChannelHandlerContext ctx) {
//        super.handleAllIdle(ctx);
//    }
//
//    private void replyHeartBeat(ChannelHandlerContext ctx) {
//        ByteBuf buf = ctx.channel().alloc().buffer();
//        buf.writeBytes(OK);
//        ctx.writeAndFlush(buf);
//    }
//
//    private void replyAuthRequire(ChannelHandlerContext ctx, byte[] data) {
//
//        // get offset bytes
//        byte[] offsetbytes = new byte[8];
//        System.arraycopy(data, data.length - 8, offsetbytes, 0, 8);
//        LOGGER.info("offsetbytes:" + readByteToHex(offsetbytes));
//        // decode
//        int offset = 0;
//        for (byte randByte : offsetbytes) {
//            offset += randByte;
//        }
//        offset = Math.abs(offset);
//        LOGGER.info("offset:" + offset);
//        byte[] passwordTable = new byte[8];
//        System.arraycopy(PWT, offset % PWT.length, passwordTable, 0, 8);
//        LOGGER.info("passwordTable:" + readByteToHex(passwordTable));
//        // decrypt to  product id
//        byte[] productId = new byte[8];
//        for (int i = 0; i < offsetbytes.length; i++) {
//            productId[i] = (byte) (offsetbytes[i] ^ passwordTable[i]);
//        }
//        LOGGER.info("productid bytes={}", readByteToHex(productId));
//        byte[] resp = new byte[AUTH_RESPONSE_HEAD.length + productId.length];
//        System.arraycopy(AUTH_RESPONSE_HEAD, 0, resp, 0, AUTH_RESPONSE_HEAD.length);
//        System.arraycopy(productId, 0, resp, AUTH_RESPONSE_HEAD.length, productId.length);
//        LOGGER.info("resp bytes={}", readByteToHex(resp));
//        ByteBuf buf = ctx.channel().alloc().buffer();
//        buf.writeBytes(resp);
//        ctx.writeAndFlush(buf);
//    }
//}