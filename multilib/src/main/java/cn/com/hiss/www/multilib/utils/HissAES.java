package cn.com.hiss.www.multilib.utils;

import android.util.Log;

import com.rt.BASE64Decoder;
import com.rt.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HissAES {
	
	private static final String TAG = HissAES.class.getSimpleName();
	public static final String AKEY = "cxc1989080811068";

	// 加密
	public static String MiniEncrypt(String sSrc, String sKey) {
		try {
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return new BASE64Encoder().encodeBuffer(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 解密
	public static String MiniDecrypt(String sSrc, String sKey) {
		try {
			Log.i(TAG, "sSrc = " + sSrc + " ; sKey = " + sKey);
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc.trim());// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			Log.i(TAG, "MiniDecrypt originalString = " + originalString);
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// /**
	// * mini aes加密test
	// * @param args
	// * @throws Exception
	// */
	// public static void main(String[] args) throws Exception {
	// // TODO Auto-generated method stub
	// String strkey = "cxc1989080811068";
	// System.out.println("strkey length = " + strkey.length());
	// String strContent = "新版港澳通行证";
	// String str = HissAES.MiniEncrypt(strContent, strkey);
	// System.out.println(str);
	// str = HissAES.MiniDecrypt(str, strkey);
	// System.out.println(str);
	// }

	private static String encrypt(String bef_aes, String password) {
		byte[] byteContent = null;
		try {
			byteContent = bef_aes.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encrypt(byteContent, password);
	}

	private static String encrypt(byte[] content, String password) {
		try {
			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			String aft_aes = parseByte2HexStr(result);
			return aft_aes; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String decrypt(String aft_aes, String password) {
		try {
			byte[] content = parseHexStr2Byte(aft_aes);
			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			String bef_aes = new String(result);
			return bef_aes; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int value = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
			result[i] = (byte) value;
		}
		return result;
	}

	private static SecretKey getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("初始化密钥出现异常");
		}
	}

	// public void test() {
	// //
	// {"n":"H1433243503568","p":"","t":"1433243521330","ts":[{"tc":"方法","tt":"啊啊"}]}
	// // String content =
	// "{\"n\":\"H1433243503568\",\"p\":\"\",\"t\":\"1433243521330\",\"ts\":[{\"tc\":\"方法\",\"tt\":\"啊啊\"}]}";
	// String content =
	// "{\"n\":\"H1433243503568\",\"p\":\"\",\"t\":\"1433243521330\",\"ts\":[]}";
	// String password = "t";
	// // 加密
	// System.out.println("加密前：" + content);
	// System.out.println("before: content.length() = " + content.length());
	// String s = encrypt(content, password);
	// System.out.println("加密后：" + s);
	// System.out.println("after: s.length() = " + s.length());
	// // 解密
	//
	// String s1 = decrypt(s, password);
	// System.out.println("解密后：" + s1);
	// }
	//
	// public static void main(String[] args) {
	// Aes aes = new Aes();
	// aes.test();
	// }

}
