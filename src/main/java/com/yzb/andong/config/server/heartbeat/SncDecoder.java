package com.yzb.andong.config.server.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.readByteToHex;

public class SncDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
			return;
		}
		System.err.println("receive start" + in.readableBytes());
		List<Byte> returnList= new ArrayList<Byte>();
		byte[] data = new byte[4];
		ByteBuf buf = in.readSlice(4);
		buf.readBytes(data);
		returnList.addAll(toByteList(data));
		buf.retain();
		System.err.println("decode data = " + readByteToHex(data));
		if (byteToInt(data[0]) == 0xFF && byteToInt(data[1]) == 0x00 && isDataValid(data)) {
			out.add(returnList);
			return;
		}
		ByteBuf buf2 = in.readSlice(6);
		byte[] data2 = new byte[6];
		buf2.readBytes(data2);
		buf.retain();
		returnList.addAll(toByteList(data2));
		System.err.println("decode data = " + readByteToHex(data2));
		System.err.println("list data = " + returnList);
		System.err.println("receive " + in.readableBytes());
		int ans = byteToInt(data2[4]) << 8 | byteToInt(data2[5]);
		ByteBuf buf3 = in.readSlice(ans>in.readableBytes()?in.readableBytes():ans);
		byte[] data3 = new byte[ans];
		buf3.readBytes(data3);
		buf3.retain();
		returnList.addAll(toByteList(data3));
		out.add(returnList);
		
		
	}

	boolean isDataValid(byte[] data) {
		if (data == null || data.length < 4) {
			return false;
		}
		int crc = 0xAA;
		for (int i = 3; i < data.length; i++) {
			crc = crc ^ byteToInt(data[i]);
		}
		System.err.println(intToByte(crc));
		System.err.println(Arrays.toString(data));
		return data[2] == intToByte(crc);
	}

	public static byte intToByte(int x) {
		return (byte) x;
	}

	public static int byteToInt(byte b) {
		return b & 0xFF;
	}

	public static List<Byte> toByteList(byte[] data) {
		List<Byte> list = new ArrayList(data.length);
		for (Byte byte1 : data) {
			list.add(byte1);
		}
		return list;
	}

}
