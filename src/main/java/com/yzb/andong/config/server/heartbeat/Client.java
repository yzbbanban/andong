package com.yzb.andong.config.server.heartbeat;//package com.yzb.andong.config.server.heartbeat;
//
//import java.util.concurrent.locks.LockSupport;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.timeout.IdleStateHandler;
//
//public class Client {
//    public static void main(String[] args) {
//        NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap
//                    .group(workGroup)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            ChannelPipeline p = socketChannel.pipeline();
//                            p.addLast(new IdleStateHandler(0, 0, 25));
//                            // p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0));
//                            p.addLast(new ClientHandler());
//                        }
//                    });
//
//            Channel ch = bootstrap.remoteAddress("127.0.0.1", 12345).connect().sync().channel();
//            ByteBuf buf = ch.alloc().buffer();
//            buf.writeBytes(ProtocolUtils.OK);
//            ch.writeAndFlush(buf);
//            LockSupport.park();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            workGroup.shutdownGracefully();
//        }
//    }
//}