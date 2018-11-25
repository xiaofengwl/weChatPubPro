package com.xfwl.token;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheckUtil {
	/**
	 * 配置静态数据
	 */
	public static final String wx_mdType = "SHA1";
	public static final String wx_token = "xiaofengwl";

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = { "xiaofengwl", timestamp, nonce };

		Arrays.sort(arr);

		StringBuffer sb = new StringBuffer();
		for (String s : arr) {
			sb.append(s);
		}
		String temp = getSha1(sb.toString());
		if (temp == null) {
			return false;
		}
		return temp.equals(signature);
	}

	public static String getSha1(String data) {
		if ((data == null) || (data.length() == 0)) {
			return null;
		}
		char[] hexDigist = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(data.getBytes());
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[(k++)] = hexDigist[(byte0 >>> 4 & 0xF)];
				buf[(k++)] = hexDigist[(byte0 & 0xF)];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("微信公众号TOKEN加密失败");
		}
		return null;
	}
}
