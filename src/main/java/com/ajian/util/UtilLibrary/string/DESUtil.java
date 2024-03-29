package com.ajian.util.UtilLibrary.string;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * @功能：基于DES方式的加密、解密工具类
 * @author 张鑫
 * @since：2012.09.27
 * 
 */
public class DESUtil {
	private static String strDefaultKey = "^cqSuiLongTec_DESUtil$";

	private static Cipher encryptCipher = null;

	private static Cipher decryptCipher = null;

	static{
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strDefaultKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private static Key getKey(byte[] arrBTmp){
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArrToHexStr(byte[] arrB){
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuilder sb = new StringBuilder(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author zhangxin
	 * @since 2012.09.27
	 */
	public static byte[] hexStrToByteArr(String strIn){
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] arrB) {
		try {
			return encryptCipher.doFinal(arrB);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encrypt(String strIn){
		return byteArrToHexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] arrB){
		try {
			return decryptCipher.doFinal(arrB);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String strIn) {
		return new String(decrypt(hexStrToByteArr(strIn)));
	}

	public static void main(String[] args) throws Exception {
		/*DESUtil des = new DESUtil();
		String str = "zhangxin";
		System.out.println("加密后的数据：" + des.encrypt(str));
		System.out.println("解密后的数据："
				+ des.decrypt("f53ba47d2ace9c3a5ffa88b4de918515"));
		logger.debug(des.encrypt(str));*/

		long b = System.nanoTime();
		DESUtil.encrypt("1");
		System.out.println("用时："+(System.nanoTime()-b));
		
		System.out.println(DESUtil.encrypt("1")+","+DESUtilsEntry.encrypt("1"));
		
		long sum_u=0,sum_e=0;
		int count=10000;
		for (int i = 0; i < count; i++) {
			long begin = System.nanoTime();
			String str = DESUtil.encrypt(String.valueOf(i));
			DESUtil.decrypt(str);
			sum_u+=(System.nanoTime()-begin);
			
			begin = System.nanoTime();
			str = DESUtilsEntry.encrypt(String.valueOf(i));
			DESUtilsEntry.decrypt(str);
			sum_e+=(System.nanoTime()-begin);
		}
		System.out.println("平均每次操作，DESUtil用时："+(sum_u/count)+"纳秒"+",DESUtilsEntry用时："+(sum_e/count)+"纳秒");
		
	}
}