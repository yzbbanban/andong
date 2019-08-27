package com.yzb.andong.config.server;//package com.yzb.andong.config.server;
//
//import com.yzb.andong.config.UnSignedConstant;
//import com.yzb.andong.config.util.HexUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应 DiscardServerHandler 继承自
// * ChannelHandlerAdapter， 这个类实现了ChannelHandler接口， ChannelHandler提供了许多事件处理的接口方法，
// * 然后你可以覆盖这些方法。 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
// */
//@Configuration
//public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
//
//    private Logger log = LoggerFactory.getLogger(this.getClass());
//
//    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//    public static Map<String, ChannelHandlerContext> chan = new ConcurrentHashMap<>();
//
//    public static Map<String, String> user = new ConcurrentHashMap<>();
//
//
//    public static Map<String, Integer> proOffset = new ConcurrentHashMap<>();
//
//    //tcp 信息
//    private static byte[] tcpMsg = new byte[]{
//            //A区头部
//            (byte) 0xFF, (byte) 0x00,
//            (byte) 0x00,
//            (byte) 0x02,
//            //B区消息
//            (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x08,
//            (byte) 0x38, (byte) 0x30, (byte) 0x38, (byte) 0x30,
//            (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30
//    };
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
//        Channel incoming = ctx.channel();
//
//        // Broadcast a message to multiple Channels
//        log.info("[SERVER] - " + incoming.id() + " 加入\n");
//        log.info("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
//
////        channels.add(ctx.channel());
//        chan.put(incoming.id() + "", ctx);
//        chan.put(incoming.id() + "", ctx);
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
//        Channel incoming = ctx.channel();
//
//        // Broadcast a message to multiple Channels
//        System.out.println("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
//
//        // A closed Channel is automatically removed from ChannelGroup,
//        // so there is no need to do "channels.remove(ctx.channel());"
//    }
//
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        channels.add(ctx.channel());
//        //为ByteBuf分配四个字节
//        ByteBuf time = ctx.alloc().buffer(4);
//        time.writeInt(1);
//        ctx.writeAndFlush(time);
//        Thread.sleep(2000);
//    }
//
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//        Channel incoming = ctx.channel();
//        //打印客户端输入，传输过来的的字符
//        System.out.println(msg);
//
//        ByteBuf in = (ByteBuf) msg;
//
//        int i = 0;
//        byte[] result = new byte[20];
//        byte[] jwt = new byte[4];
//        // FF 00 FB 02
//        // 00 01 00 01 00 08
//        // 65 6D D1 0C 0C 57 AB 7E
//        byte[] id = new byte[18];
//        try {
//            while (in.isReadable()) {
//                byte b = in.readByte();
//                result[i] = b;
//                if (i <= 3) {
//                    jwt[i] = b;
//                }
//                if (i <= 18) {
//                    id[i] = b;
//                }
//                i++;
//            }
//        } catch (Exception e) {
//            ctx.writeAndFlush("error");
//        }
//
//        log.info("result----> " + toHexString(result));
//        log.info("jwt----> " + toHexString(jwt));
//
//
//        ByteBuf buf = ctx.alloc().buffer(4);
//        byte[] closeMsg = new byte[]{
//                //A区头部
//                (byte) 0xFF, (byte) 0x00,
//                (byte) 0xAA,
//                (byte) 0x00
//        };
//
//        if (Arrays.equals(jwt, closeMsg)) {
//            //认证
//
//            log.info("认证了，认证了");
//            //生成8个随机字节
//            Random rand = new Random();
//            //记录 offset
//            int offset = 0;
//
//            for (int j = 0; j < 8; j++) {
//                int res = rand.nextInt(10);
////                System.out.println("===> " + res);
//                Integer or = Integer.parseInt(String.valueOf(res));
//                tcpMsg[10 + j] = or.byteValue();
//                offset += or;
//            }
//
//            offset = offset & 255;
//            //channel id  ： offsetHexUtil
//            proOffset.put(incoming.id() + "", offset);
//
//            System.out.println("-----offset-----> " + offset);
//
//            int c = 0xAA;
//            for (int j = 3; j < tcpMsg.length; j++) {
//                Integer or = Integer.parseInt(HexUtil.byteToHex(tcpMsg[j]), 16);
//                c = c ^ or;
//            }
//
//
//            tcpMsg[2] = (byte) c;
//            buf.writeBytes(tcpMsg);
//            ctx.writeAndFlush(buf);
//            log.info("tcpMsg----> " + toHexString(tcpMsg));
//        } else if (id.length == 18) {
//            //获取产品 id 处理真实数据
//            // FF 00 FB 02
//            // 00 01 00 01 00 08
//            // 65 6D D1 0C 0C 57 AB 7E
//            Integer offs = proOffset.get(incoming.id() + "");
//            if (offs == null) {
//                buf.writeBytes(result);
//                ctx.writeAndFlush(buf);
//            } else {
//                //获取加密数据
//                byte[] unSign = UnSignedConstant.UN_SIGN;
//
//                byte[] tempId = new byte[8];
//
//                for (int j = 10; j < id.length; j++) {
//                    Integer or1 = Integer.parseInt(HexUtil.byteToHex(unSign[offs + j - 10]), 16);
//                    Integer or2 = Integer.parseInt(HexUtil.byteToHex(id[j]), 16);
//                    tempId[j - 10] = (byte) (or1 ^ or2);
//                }
//
//                System.out.println("鉴权id " + toHexString(tempId));
//
//                log.info("鉴权----> " + toHexString(closeMsg));
//                buf.writeBytes(closeMsg);
//                ctx.writeAndFlush(buf);
//
//            }
//
//        } else {
//            log.info("else----> " + toHexString(result));
//            buf.writeBytes(result);
//            ctx.writeAndFlush(buf);
//        }
////        // 绑定用户
////        if (msg.toString().contains("#")) {
////            String userId = msg.toString().replace("#", "");
////            user.put(userId, "" + ctx.channel().id());
////            ctx.writeAndFlush("ok");
////        } else {
////            String[] tar = msg.toString().split(":");
////            try {
////                String ch = user.get(tar[0]);
////                ChannelHandlerContext target = chan.get(ch);
////                target.writeAndFlush(tar[1]);
////            } catch (Exception e) {
////                ctx.writeAndFlush("not on line");
////            }
////        }
//
//    }
//
//    public static void main1(String[] args) {
//
//        Random rand = new Random();
//        for (int j = 0; j < 8; j++) {
//
////                System.out.println("===> " + res);
//            Integer or = Integer.parseInt(String.valueOf(15));
//            tcpMsg[10 + j] = or.byteValue();
//        }
//
//        System.out.println("====> " + toHexString(tcpMsg));
//
//        String ss = "39.108.103.128";
//        byte[] closeMsg = new byte[ss.length()];
//        for (int i = 0; i < ss.length(); i++) {
//            closeMsg[i] = (byte) ss.charAt(i);
//        }
//        System.out.println("---> " + toHexString(closeMsg));
//        //33 39 2e 31 30 38 2e 31 30 33 2e 31 32 38
//
//
//        String url = "39.108.103.128";
//        String u = url.split(":")[0];
//        System.out.println("---> onResume: " + u);
//
//        //计算长度
//        int tcpLength = 15 + u.length();
//        int bodyLength = u.length();
//        byte[] tcpMsg2 = new byte[tcpLength];
//
//        for (int i = 0; i < tcpLength; i++) {
//            if (i < 9) {
//                tcpMsg2[0] = (byte) 0xFF;
//                tcpMsg2[1] = (byte) 0x00;
//                tcpMsg2[2] = (byte) 0x00;
//                tcpMsg2[3] = (byte) 0x04;
//                tcpMsg2[4] = (byte) 0x00;
//                tcpMsg2[5] = (byte) 0x01;
//                tcpMsg2[6] = (byte) 0x00;
//                tcpMsg2[7] = (byte) 0x01;
//                tcpMsg2[8] = (byte) 0x00;
//            } else if (i == 9) {
//                Integer or = Integer.parseInt(String.valueOf(tcpLength - 10));
//                tcpMsg2[9] = or.byteValue();
//            } else if (i < 10 + bodyLength) {
//                tcpMsg2[i] = (byte) u.charAt(i - 10);
//            } else {
//                tcpMsg2[tcpLength - 5] = (byte) 0x00;
//                tcpMsg2[tcpLength - 4] = (byte) 0x39;
//                tcpMsg2[tcpLength - 3] = (byte) 0x39;
//                tcpMsg2[tcpLength - 2] = (byte) 0x39;
//                tcpMsg2[tcpLength - 1] = (byte) 0x35;
//            }
//
//        }
//        System.out.println("---> " + toHexString(tcpMsg2));
//
//
//        String u1 = "ban";
//        String s = "11111111";
//
//        int wifiLength = 11 + u1.length() + s.length();
//        byte[] wifiMsg2 = new byte[wifiLength];
//
//        for (int i = 0; i < wifiLength; i++) {
//            if (i < 9) {
//                wifiMsg2[0] = (byte) 0xFF;
//                wifiMsg2[1] = (byte) 0x00;
//                wifiMsg2[2] = (byte) 0x00;
//                wifiMsg2[3] = (byte) 0x05;
//                wifiMsg2[4] = (byte) 0x00;
//                wifiMsg2[5] = (byte) 0x01;
//                wifiMsg2[6] = (byte) 0x00;
//                wifiMsg2[7] = (byte) 0x01;
//                wifiMsg2[8] = (byte) 0x00;
//            } else if (i == 9) {
//                Integer or = Integer.parseInt(String.valueOf(wifiLength - 10));
//                wifiMsg2[9] = or.byteValue();
//            } else if (i < 10 + u1.length()) {
//                wifiMsg2[i] = (byte) u1.charAt(i - 10);
//            } else if (i == u1.length() + 10) {
//                wifiMsg2[i] = (byte) 0x00;
//            } else {
//                wifiMsg2[i] = (byte) s.charAt(wifiLength - i - 1);
//            }
//
//        }
//
//        System.out.println("\n---> " + toHexString(wifiMsg2));
//        // 0 1  2   3  4  5  6  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
//        //ff 00 00 05 00 01 00 01 00 0C 62 61 6E 00 31 31 31 31 31 31 31 31 31
//    }
//
//    /**
//     * 客户端 失去连接
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);
//    }
//
//    private int loss_connect_time = 0;
//
//    /**
//     * 心跳机制  用户事件触发
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            IdleState state = event.state();
//            if (state == IdleState.READER_IDLE) {
//                ctx.writeAndFlush("no client");
//                loss_connect_time++;
//                log.info(String.valueOf(10 * loss_connect_time) + "秒没有接收到客户端的信息了");
////                if (loss_connect_time >= 100) {
////                    log.info("------------服务器主动关闭客户端链路");
////                    ctx.channel().close();
////                }
//            } else if (state == IdleState.WRITER_IDLE) {
//                ctx.writeAndFlush("no server link");
//            }
//
//
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//
//    }
//
//    /***
//     * 这个方法会在发生异常时触发
//     *
//     * @param ctx
//     * @param cause
//     */
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        /**
//         * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO
//         * 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
//         * 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不 同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
//         */
//        // 出现异常就关闭
//        cause.printStackTrace();
//        ctx.close();
//    }
//
//
//    /**
//     * 数组转成十六进制字符串
//     *
//     * @param b
//     * @return
//     */
//    public static String toHexString(byte[] b) {
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < b.length; ++i) {
//            buffer.append(toHexString1(b[i]));
//        }
//        return buffer.toString();
//    }
//
//
//    public static String toHexString1(byte b) {
//        String s = Integer.toHexString(b & 0xFF);
//        if (s.length() == 1) {
//            return "0" + s;
//        } else {
//            return s;
//        }
//    }
//
////    public static void main(String[] args) {
////
////        Random rand = new Random();
////        int offset = 0x00;
////        byte[] temp = new byte[8];
////        for (int j = 0; j < 8; j++) {
////            int res = rand.nextInt(10);
////            Integer or = Integer.parseInt(String.valueOf(res), 16);
////            tcpMsg[10 + j] = or.byteValue();
////            temp[j] = or.byteValue();
////            offset += or;
////        }
////        System.out.println(offset);
////        offset = offset & 255;
////        System.out.println("2--->" + offset);
////        System.out.println("3--->" + (0x2d & 0xFF));
////        System.out.println("yuan--->" + (0x24 & 0xFF));
////    }
//
//
//}
