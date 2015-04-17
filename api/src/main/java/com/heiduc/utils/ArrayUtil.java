

package com.heiduc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {

	public static List<byte[]> makeChunks(byte[] data, int chunkSize) {
		List<byte[]> result = new ArrayList<byte[]>();
		long n = data.length / chunkSize;
		int finalChunkSize = data.length % chunkSize;
		int i = 0;
		int start;
		int end;
		for (i = 0; i < n; i++) {
			start = i * chunkSize;
			end = start + chunkSize;
			result.add(Arrays.copyOfRange(data, start, end));
		}
		if (finalChunkSize > 0) {
			start = i * chunkSize;
			end = start + finalChunkSize;
			result.add(Arrays.copyOfRange(data, start, end));
		}
		return result;
	}
	
	public static byte[] packChunks(List<byte[]> data) {
		int size = 0;
		for (byte[] chunk : data) {
			size += chunk.length;
		}
		byte[] result = new byte[size];
		int start = 0;
		for (byte[] chunk : data) {
			System.arraycopy(chunk, 0, result, start, chunk.length);
			start += chunk.length;
		}
		return result;
	}
	
}
