package com.springMVC.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.*;

public class encryptUtil {
	private static String TOKEN_KEY="i37iq3nlu3a4b6v9yrjxrjeit3qhptg534v8u54hj40joo0hoo6lzb60s7otiamm32j"
			+ "k60y5hg89jr0u3pwu816868bi23x5r6nmtjcybepum39l45tu3axy5dwz9rb8dol2lewvxgwl6s91h50334zjj3du77xnr7yzkl98fb5feaj3055bu1cyf6ixsdnplw5br4ri";
	
	private static String TOKEY_KEY_UUID = "ceeb83109a59489abff4e3b98fab59ec";
	
    public static void main(String ...arg)  throws Exception{
    	
//    	String psw = DigestUtils.md5DigestAsHex(("123456annie").getBytes());
    	String token="20160309151515";
    	String encodeToken="F7F88D8947F59D250B6607650F8663D048B19FA790A4FB5EE096D964F683D818F277F6C4F65BE921C3D131BC7424E41914459FD557E78E8F11C9669301C1F429B65A7EEAAA8B0D066C81FABA1F71D178ECD58820745A3B22513249983FBEB9DF2393B25F80081C4AB78A56DEBF6AE6425FF382CBCA21E22D5BB6EF3CCADD8A7EA7474839C712D06877A6110D888C98BC6DDD3153E027AE2EC16A7A889DF67ADD987694C438BA1E69B30F7AD278C2E488A4CD7C8E9FF85EA49F231B4586F0D6D300C1ACADEC9875287B53D029603359F7387621B771383D6A2D279CFE99D0F8E5";
//    	EncryptUtil encryptUtil=new EncryptUtil();
//    	String psw = encryptUtil.aesDecrypt(encodeToken, TOKEY_KEY_UUID);
    	String psw = encryptUtil.aesEncrypt(token, TOKEY_KEY_UUID);
    	System.out.println(psw);
    }
    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new BASE64Encoder().encode(bytes);
    }

    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}
