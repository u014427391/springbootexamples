package com.example.springcloud.gateway.util;

import java.security.MessageDigest;

/**
 *	<pre>
 *	    加密工具类
 *	</pre>
 */
public class EncryptUtils {

	/**
	 * 	sha256签名算法
	 */
	public static String toSHA256(String str) throws Exception {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (Exception e) {
			throw e;
		}
		return encodeStr;
	}

	/**
	 * byte转换成16进制
	 */
	protected static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}
}
