package com.platform.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密算法
 * @author Administrator
 *
 */
public class EncryptUtil {
	
	/**
	 * 使用MD5加密
	 * @param inStr
	 * @return
	 * @throws Exception
	 */
	public static String encryptMd5(String psw) throws Exception {
		if(psw==null){
			return "";
		}else{
			MessageDigest md = null;
			//psw+="liaojy";
			try {
				md = MessageDigest.getInstance("MD5");
				byte[] digest = md.digest(psw.getBytes());
				return new String(Base64.encodeBase64(digest));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(EncryptUtil.encryptMd5("1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
