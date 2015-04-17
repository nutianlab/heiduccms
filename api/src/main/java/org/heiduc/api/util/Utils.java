package org.heiduc.api.util;

public class Utils {
	
	public static long bytes2long(byte[] b) {
		long temp = 0;
		long res = 0;
		for (int i=0;i<8;i++) {
			res <<= 8;
			temp = b[i] & 0xff;
			res |= temp;
		}
		return res;
	}
	
	public static byte[] long2bytes(long num) {
		byte[] b = new byte[8];
		for (int i=0;i<8;i++) {
			b[i] = (byte)(num>>>(56-(i*8)));
		}
		return b;
	}

}
