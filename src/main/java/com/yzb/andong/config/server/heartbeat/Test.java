package com.yzb.andong.config.server.heartbeat;//package com.yzb.andong.config.server.heartbeat;
//
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.PWT;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.RANDOM_SIZE;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.REQUIRE_AUTH_HEAD;
//import static com.yzb.andong.config.server.heartbeat.ProtocolUtils.readByteToHex;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Test {
//	public static void main(String args[]) {
//		byte[] resp = new byte[REQUIRE_AUTH_HEAD.length + RANDOM_SIZE];
//		int offset = 64;
//		byte[] snTable = { (byte) 0xAB, (byte) 0xC0, 0x02, 0x65, 0x13, 0x01, 0x05, 0x06 };
//		byte[] passTable = new byte[8];
//		byte[] enSn = new byte[8];
//		System.arraycopy(PWT, offset % PWT.length, passTable, 0, 8);
//		for (int i = 0; i < snTable.length; i++) {
//			enSn[i] = intToByte(byteToInt(snTable[i]) ^ byteToInt(passTable[i]));
//		}
//		System.arraycopy(REQUIRE_AUTH_HEAD, 0, resp, 0, REQUIRE_AUTH_HEAD.length);
//		System.arraycopy(enSn, 0, resp, REQUIRE_AUTH_HEAD.length, RANDOM_SIZE);
//		resp[2] = getCRC(resp);
//		StringBuilder b2 = new StringBuilder();
//		for (String b : ByteToHex(resp)) {
//			b2.append(b);
//			b2.append("");
//		}
//		System.err.println("replyAuthRequire resp:" + b2.toString());
//	}
//
//	static byte getCRC(byte[] data) {
//		int crc = 0xAA;
//		for (int i = 3; i < data.length; i++) {
//			crc = crc ^ byteToInt(data[i]);
//		}
//		return intToByte(crc);
//	}
//
//	public static byte intToByte(int x) {
//		return (byte) x;
//	}
//
//	public static int byteToInt(byte b) {
//		return b & 0xFF;
//	}
//
//	public static List<String> ByteToHex(byte[] bytes) {
//		List<String> bytestr = new ArrayList<>(bytes.length);
//		for (byte b : bytes) {
//			String s = Integer.toString(b & 0xff, 16);
//			bytestr.add((s.length() == 2 ? s : ("0" + s)) + " ");
//		}
//		return bytestr;
//	}
//
//}
