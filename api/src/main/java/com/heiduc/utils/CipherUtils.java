

package com.heiduc.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CipherUtils {

	private static final Log logger = LogFactory.getLog(CipherUtils.class);

	private static byte[] iv = { 12, 4, 7, 23, 8, 1, 0, 3, 87, 120, 39, 2, 1, 0, 2, 1 };
    private static IvParameterSpec ivspec = new IvParameterSpec(iv);

	public static String encrypt(String data, String password) {
		try {
			byte[] key = new Base64().decode(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);
			byte[] dataBytes = data.getBytes("UTF-8");
			byte[] d = cipher.doFinal(dataBytes);
			return Hex.encodeHexString(d);

		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}

	public static String decrypt(String data, String password) {
		try {
			byte[] key = new Base64().decode(password);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);
			byte[] d = (byte[])new Hex().decode(data);
			byte[] decrypted = cipher.doFinal(d);
			return new String(decrypted, "UTF-8");

		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}

	public static String generateKey() {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        kgen.init(128);
		    return new Base64().encodeToString(kgen.generateKey().getEncoded());
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error generating secret key", e);
			return "";
		}
	}

}
