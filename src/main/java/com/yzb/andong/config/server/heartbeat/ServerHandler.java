package com.yzb.andong.config.server.heartbeat;

import com.yzb.andong.config.util.SpringUtil;
import com.yzb.andong.service.ifac.ProductService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.*;

public class ServerHandler extends HeartbeatHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);
    /* 缓存offset */
    private ConcurrentHashMap<String, Integer> offsetMap = new ConcurrentHashMap<>();
    /* 缓存已经认证的channel */
    private CopyOnWriteArrayList<Channel> authChannel = new CopyOnWriteArrayList<Channel>();
    /* 身份认证 */
    private static final byte ID_IDENTIFY = 0x02;
    /* 发送图片 */
    private static final byte SEND_IMAGE = 0x40;

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        // TODO Auto-generated method stub

    }

    public static final String PATH = "/root/pic/";

    public static ConcurrentHashMap<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Channel, List<Byte>> tempMap = new ConcurrentHashMap<Channel, List<Byte>>();

    public static ConcurrentHashMap<Channel, List<byte[]>> picMap = new ConcurrentHashMap<Channel, List<byte[]>>();

    public static ConcurrentHashMap<Channel, Long> channelTimeMap = new ConcurrentHashMap<Channel, Long>();


    public static void main(String[] args) {
        ConcurrentHashMap<String, Map<String, String>> channelMap = new ConcurrentHashMap<>();

        Map<String, String> map1 = new HashMap<>(2);
        Map<String, String> map2 = new HashMap<>(2);
        map1.put("1", "222");
        map2.put("2", "3333");


        channelMap.put("1111", map1);
        channelMap.put("3333", map2);
        System.out.println("==1===>" + channelMap);
        System.out.println("==2===>" + channelMap.values().remove(map2));
        System.out.println("==3===>" + channelMap);


    }

//	@Autowired
//	private ProductService productService;

    private ProductService productService = SpringUtil.getApplicationContext().getBean(ProductService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        byte[] data = new byte[buf.readableBytes()];
        // 数据校验,不通过直接返回
        buf.readBytes(data);
//		System.err.println("buffer長度" + data.length);
        if (byteToInt(data[0]) != 0xFF || byteToInt(data[1]) != 0x00) {
            List<Byte> list = tempMap.get(ctx.channel());
            if (null == list) {
                ByteBuf buff = ctx.channel().alloc().buffer();
                buff.writeBytes(NG);
                ctx.writeAndFlush(buff);
                return;
            }
            list.addAll(toByteList(data));
            LOGGER.info("继续接收不完整请求，长度=" + list.size());
            int length = (byteToInt(list.get(8)) << 8 | byteToInt(list.get(9))) + 10;
            LOGGER.info("校验长度=" + length);
            if (list.size() >= length || list.size() == 1450) {
                LOGGER.info("开始处理，长度=" + list.size());
                channelRead(ctx, toByteArray(list));
                tempMap.remove(ctx.channel());
            }
        } else {
            if (data[3] == 0x40 && data.length < ((byteToInt(data[8]) << 8 | byteToInt(data[9])) + 10)) {
                LOGGER.info("收到不完整的上传照片请求");
                List<Byte> list = new ArrayList<Byte>();
                list.addAll(toByteList(data));
                tempMap.put(ctx.channel(), list);
                return;
            } else {
                channelRead(ctx, data);
            }
        }
    }

    protected void channelRead(ChannelHandlerContext ctx, byte[] data) throws Exception {
//        超时时间重置
//        channelTimeMap.put(ctx.channel(), System.currentTimeMillis());
        Channel channel = ctx.channel();
        String channelId = ctx.channel().id().asLongText();
        System.err.println("时间：" + System.currentTimeMillis() + " id: "
                + channelId + "处理数据,长度=" + data.length + readByteToHex(data));

        if ((data[3] == (byte) 0x02 && 18 == data.length) || authChannel.contains(channel)) {
            if (Arrays.equals(data, ProtocolUtils.OK)) {
                replyHeartBeat(ctx);
                return;
            }
        } else {
            // require client to authorize
            replyAuthRequire(ctx, channelId);
            return;
        }
        // authorization request
        // 判断产品认证类型
        if (data[3] == (byte) 0x02 && 18 == data.length) {
            if (checkIsValid(channelId, ctx.channel(), ctx, data)) {

                replyHeartBeat(ctx);
                // 清除offset缓存
                offsetMap.remove(channelId);
                LOGGER.info("authorization ok");
                return;
            } else {
                LOGGER.error("authorization invalid");
                ByteBuf buff = ctx.channel().alloc().buffer();
                buff.writeBytes(NG);
                ctx.writeAndFlush(buff);
                ctx.close();
                return;
            }
        } else if (judgeType(data, SEND_IMAGE)) {
            //第几次传输
            int num = byteToInt(data[6]) << 8 | byteToInt(data[7]);
            if (isDataInvalid(data)) {
                ByteBuf buff = ctx.channel().alloc().buffer();
                buff.writeBytes(NG);
                ctx.writeAndFlush(buff);
                LOGGER.error("缓存第" + num + "条图片数据失败，" + "+图片数据格式不争确");
                return;
            }
            //获取map中的图片
            List<byte[]> picList = picMap.get(ctx.channel());
            if (null == picList) {
                picList = new ArrayList<byte[]>();
                picMap.put(ctx.channel(), picList);
            }
            if (picList.size() > num) {
                LOGGER.info("上一图片被覆盖，当前传输为第：" + num + "条数据，图片数据已传：" + picList.size());
                picList = new ArrayList<>();
                picMap.put(ctx.channel(), picList);
            }
            if (num == picList.size()) {
                LOGGER.info("重复收到第" + num + "条图片数据，回复OK");
                replyHeartBeat(ctx);
                return;
            } else {
                picList.add(data);
                LOGGER.info("保存第" + num + "条图片数据,总数据为： " + picList.size());
            }
            //获取要传多少次
            int sum = byteToInt(data[4]) << 8 | byteToInt(data[5]);
//			if (null == picMap.get(ctx.channel())) {
//				picMap.put(ctx.channel(), new ArrayList<Byte>());
//
//			}
//			for (int i = 10; i < data.length; i++) {
//				picMap.get(ctx.channel()).add(data[i]);
//			}
//			if(null==testMap.get(ctx.channel()))
//					{
//				testMap.put(ctx.channel(), 0);
//					}
//			testMap.put(ctx.channel(), testMap.get(ctx.channel())+1);
            if (num == sum && sum > 0 && picList.size() == sum) {
                System.err.println("start save pic");
                String name = UUID.randomUUID().toString() + ".jpg";
                File path = new File(PATH);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File file = new File(PATH + name);
                FileOutputStream output = null;
                ByteArrayInputStream input = null;
                BufferedOutputStream out = null;
                try {
                    output = new FileOutputStream(file);
                    out = new BufferedOutputStream(output);
                    byte[] bytes = buildReturnByte(picList);
//					readBytes(picMap.get(ctx.channel()), bytes);
                    input = new ByteArrayInputStream(bytes);
                    int index = 0;
                    while (index >= 0) {
                        index = input.read();
                        out.write(index);
                    }
                    out.flush();
                    picMap.remove(ctx.channel());
                    String productId = null;
                    for (Entry<String, ChannelHandlerContext> entry : channelMap.entrySet()) {
                        if (entry.getValue() == ctx) {
                            productId = entry.getKey();
                        }
                    }
                    saveImg(name, productId);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    LOGGER.error("IO", e);
                } catch (IOException e) {
                    LOGGER.error("IO", e);
                } finally {
                    if (null != input) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (null != output) {
                        try {
                            output.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            replyHeartBeat(ctx);
//			} else {
//
//			}
//			// 图片拼接
//			String imgStr = stitchImage(data);
//			// 保存图片到数据库
//			saveImg(imgStr, channelId);
        } else if (judgeType(data, (byte) 0x20)) {
            Attribute<byte[]> attr = channel.attr(AttributeKey.valueOf(channel.id().asLongText()));
            synchronized (attr) {
                // 当前消息就是正在等待返回的响应消息,同步消息
                attr.set(data);
                attr.notify();
                replyHeartBeat(ctx);
                return;
            }
        }
    }

//	@Override
//	protected void handleData(ChannelHandlerContext ctx, ByteBuf buf) {
//		handleData(ctx, buf, false);
//	}

//	protected void handleData(ChannelHandlerContext ctx, ByteBuf buf, boolean isSub) {
//		byte[] data = null;
//		if (isSub) {
//			data = new byte[byteMap.get(ctx.channel()).size()];
//			readBytes(byteMap.get(ctx.channel()), data);
//			byteMap.remove(ctx.channel());
//		} else {
//			data = new byte[buf.readableBytes()];
//			// 数据校验,不通过直接返回
//			buf.readBytes(data);
//		}
//		if (byteToInt(data[0]) == 0xFF && byteToInt(data[1]) == 0x00 && byteToInt(data[3]) != 0x00) {
//			if ((!isDataInvalid(data)) || judgeType(data, SEND_IMAGE)) {
//				if (null != byteMap.get(ctx.channel())) {
//					handleData(ctx, buf, true);
//				}
//			} else {
//				if (null != byteMap.get(ctx.channel())) {
//					byteMap.get(ctx.channel()).addAll(toByteList(data));
//					replyHeartBeat(ctx);
//					return;
//				}
//			}
//
//		} else {
//			if (null != byteMap.get(ctx.channel())) {
//				byteMap.get(ctx.channel()).addAll(toByteList(data));
//				return;
//			}
//		}
//		if (isDataInvalid(data)) {
//
//			byteMap.put(ctx.channel(), toByteList(data));
//			return;
//		}
//		LOGGER.info("server receive data:" + readByteToHex(data));
//		Channel channel = ctx.channel();
//		String channelId = ctx.channel().id().asLongText();
//
//		if ((data[3] == (byte) 0x02 && 18 == data.length) || authChannel.contains(channel)) {
//			if (Arrays.equals(data, ProtocolUtils.OK)) {
//				replyHeartBeat(ctx);
//				return;
//			}
//		} else {
//			// require client to authorize
//			replyAuthRequire(ctx, channelId);
//			return;
//		}
//		// authorization request
//		// 判断产品认证类型
//		if (data[3] == (byte) 0x02 && 18 == data.length) {
//			if (checkIsValid(channelId, ctx.channel(), ctx, data)) {
//
//				replyHeartBeat(ctx);
//				// 清除offset缓存
//				offsetMap.remove(channelId);
//				LOGGER.info("authorization ok");
//				return;
//			} else {
//				LOGGER.error("authorization invalid");
//				ByteBuf buff = ctx.channel().alloc().buffer();
//				buff.writeBytes(NG);
//				ctx.writeAndFlush(buff);
//				ctx.close();
//				return;
//			}
//		} else if (judgeType(data, SEND_IMAGE)) {
//
//			if (data[4] == data[6] && data[5] == data[7]) {
//				File file = new File("/root/" + UUID.randomUUID().toString() + ".jpg");
//				FileOutputStream output = null;
//				ByteArrayInputStream input = null;
//				BufferedOutputStream out = null;
//				try {
//					output = new FileOutputStream(file);
//					out = new BufferedOutputStream(output);
//					byte[] bytes = new byte[picMap.get(ctx.channel()).size()];
//					readBytes(picMap.get(ctx.channel()), bytes);
//					input = new ByteArrayInputStream(bytes);
//					int index = 0;
//					while (index > 0) {
//						index = input.read();
//						out.write(index);
//					}
//					out.flush();
//					picMap.remove(ctx.channel());
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					if (null != input) {
//						try {
//							input.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					if (null != output) {
//						try {
//							output.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			} else {
//			}
////			} else {
////
////			}
////			// 图片拼接
////			String imgStr = stitchImage(data);
////			// 保存图片到数据库
////			saveImg(imgStr, channelId);
//		} else {
//			ByteBuf buff = ctx.channel().alloc().buffer();
//			buff.writeBytes(NG);
//			ctx.writeAndFlush(buff);
//		}
//
//	}

    private void saveImg(String imgStr, String channelId) {
        productService.saveImg(imgStr, channelId);
    }

    private String stitchImage(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        String imgStr = encoder.encode(data).trim();
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream os = null;
        StringBuilder image = new StringBuilder();
        // 需要发送次数=已发次数
        if (data[4] == data[6] && data[5] == data[7]) {
            try {
                byte[] imgbyte = decoder.decodeBuffer(imgStr);
                File file = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
                os = new FileOutputStream(file);
                os.write(imgbyte, 0, imgbyte.length);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            image.append(imgStr);
        }
        return image.toString();
    }

    private boolean judgeType(byte[] data, byte idIdentify) {
        if (data[3] == idIdentify) {
            return true;
        }
        return false;
    }

    private boolean checkIsValid(String channelId, Channel channel, ChannelHandlerContext ctx, byte[] data) {
        // get last 8 bytes
        byte[] encryptedSequence = new byte[8];
        System.arraycopy(data, data.length - 8, encryptedSequence, 0, 8);
        LOGGER.info("encryptedSequence={}", encryptedSequence);
        // decode
        Integer offset = offsetMap.get(channelId);
        byte[] passwordTable = new byte[8];
        if (offset != null) {
            System.arraycopy(PWT, offset % PWT.length, passwordTable, 0, 8);
            LOGGER.info("passwordTable={}", passwordTable);
            // decrypt to product id
            StringBuilder productId = new StringBuilder();
            for (int i = 0; i < encryptedSequence.length; i++) {
                // 获取产品唯一编号
                String s = Integer.toString(byteToInt(encryptedSequence[i]) ^ byteToInt(passwordTable[i]), 16);
                productId.append(s.length() == 2 ? s : ("0" + s));
            }
            LOGGER.info("product id={}", productId);
            // getFromDB
            if (existInDB(productId.toString())) {
                authChannel.add(channel); // 做一下数组操作
                channelMap.put(productId.toString(), ctx);
                return true;
            }
        }
        return false;
    }

    private boolean existInDB(String productId) {
        Boolean flag = false;
        // 检查产品唯一编号是否存在
        int count = productService.getProUidCount(productId);
        if (count > 0)
            flag = true;
        return flag;
    }

    private void replyAuthRequire(ChannelHandlerContext ctx, String channelId) {
        LOGGER.info("replyAuthRequire==>" + channelId);
        byte[] randBytes = ProtocolUtils.randBytes(RANDOM_SIZE);
        LOGGER.info("rand bytes:{}", readByteToHex(randBytes));
        // 计算sum
        int offset = 0;
        for (byte randByte : randBytes) {
            offset += byteToInt(randByte);
        }
        // byte是有符号数，可能产生负数，取绝对值
        offset = offset & 0xFF;
        offsetMap.put(channelId, offset);
        byte[] resp = new byte[REQUIRE_AUTH_HEAD.length + RANDOM_SIZE];
        System.arraycopy(REQUIRE_AUTH_HEAD, 0, resp, 0, REQUIRE_AUTH_HEAD.length);
        System.arraycopy(randBytes, 0, resp, REQUIRE_AUTH_HEAD.length, RANDOM_SIZE);
        resp[2] = getCRC(resp);
        LOGGER.info("replyAuthRequire resp:" + readByteToHex(resp));
        ByteBuf buf = ctx.channel().alloc().buffer();
        buf.writeBytes(resp);
        ctx.writeAndFlush(buf);
    }

    @Override
    protected void replayHeartChannel(ChannelHandlerContext ctx) {
        if (ctx.channel().isActive()) {
            LOGGER.info("replayHeartChannel==>" + ctx.channel().id().asLongText());
            ByteBuf buf = ctx.channel().alloc().buffer();
            buf.writeBytes(OK);
            ctx.writeAndFlush(buf);
        }
    }


    @Override
    protected void removeChannel(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String channelId = ctx.channel().id().asLongText();
        boolean res = channelMap.values().remove(ctx);
        LOGGER.info("remove channel===>id:{}==ctx: {}", channelId, channel);
        authChannel.remove(channel);
    }

    private void replyHeartBeat(ChannelHandlerContext ctx) {
        LOGGER.info("replayHeartBeat==>" + ctx.channel().id().asLongText());
        ByteBuf buf = ctx.channel().alloc().buffer();
        buf.writeBytes(OK);
        ctx.writeAndFlush(buf);
    }

    boolean isDataInvalid(byte[] data) {
        if (data == null || data.length < 4) {
            return true;
        }
        int crc = 0xAA;
        for (int i = 3; i < data.length; i++) {
            crc = crc ^ byteToInt(data[i]);
        }
        System.err.println(intToByte(crc));
        System.err.println(Arrays.toString(data));
        return data[2] != intToByte(crc);
    }

    byte getCRC(byte[] data) {
        int crc = 0xAA;
        for (int i = 3; i < data.length; i++) {
            crc = crc ^ byteToInt(data[i]);
        }
        return intToByte(crc);
    }

    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public void readBytes(List<Byte> list, byte[] bytes) {
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
    }

    public static List<Byte> toByteList(byte[] data) {
        List<Byte> list = new ArrayList(data.length);
        for (Byte byte1 : data) {
            list.add(byte1);
        }
        return list;
    }

    public static byte[] toByteArray(List<Byte> list) {
        byte[] byteArr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            byteArr[i] = list.get(i);
        }
        return byteArr;
    }

    public static byte[] buildReturnByte(List<byte[]> list) {
        int length = 0;
        for (byte[] bs : list) {
            length = length + bs.length;
        }
        length = length - list.size() * 10;
        int offset = 0;
        byte[] ans = new byte[length];
        for (byte[] bs : list) {
            for (int i = 10; i < bs.length; i++) {
                ans[offset] = bs[i];
                offset++;
            }
        }
        return ans;
    }

//	public static byte[][] toFileByte(List<Byte> data) {
//		byte[][] bytes = null;
//		if (0 == data.size() % 8) {
//			bytes = new byte[data.size() / 8][8];
//		} else {
//			bytes = new byte[data.size() / 8 + 1][8];
//		}
//		for(int i=0;i<data.)
//	}


    public static byte getCRCResult(byte[] bytes) {
        int c = 0xAA;
        for (int j = 3; j < bytes.length; j++) {
            Integer or = Integer.parseInt(byteToHex(bytes[j]), 16);
            c = c ^ or;
        }

        return (byte) c;
    }

    private static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

}