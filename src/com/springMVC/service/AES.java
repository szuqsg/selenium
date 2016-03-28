package com.springMVC.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES
{
	
    private static SecretKeySpec secretKey ;
    private static byte[] key ;
    
	private static String decryptedString;
	private static String encryptedString;
 
    
    public static void setKey(String myKey){
    	
   
    	MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			System.out.println(key.length);
			sha = MessageDigest.getInstance("SHA1");
			key = sha.digest(key);
	    	key = Arrays.copyOf(key, 16); // use only first 128 bit
	    	System.out.println(key.length);
	    	System.out.println(new String(key,"UTF-8"));
		    secretKey = new SecretKeySpec(key, "AES");
		    
		    
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	    	  
	

    }
    
    public static String getDecryptedString() {
		return decryptedString;
	}

	public static void setDecryptedString(String decryptedString) {
		AES.decryptedString = decryptedString;
	}

    public static String getEncryptedString() {
		return encryptedString;
	}

	public static void setEncryptedString(String encryptedString) {
		AES.encryptedString = encryptedString;
	}

	public static String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        	System.out.println(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            setEncryptedString(Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
        
        }
        catch (Exception e)
        {
           
            System.out.println("Error while encrypting: "+e.toString());
        }
        return null;

    }

    public static String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
           
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));
            
        }
        catch (Exception e)
        {
         
            System.out.println("Error while decrypting: "+e.toString());

        }
        return null;
    }

    private static String TOKEN_KEY="i37iq3nlu3a4b6v9yrjxrjeit3qhptg534v8u54hj40joo0hoo6lzb60s7otiamm32j"
			+ "k60y5hg89jr0u3pwu816868bi23x5r6nmtjcybepum39l45tu3axy5dwz9rb8dol2lewvxgwl6s91h50334zjj3du77xnr7yzkl98fb5feaj3055bu1cyf6ixsdnplw5br4ri";
	
	private static String TOKEY_KEY_UUID = "ceeb83109a59489abff4e3b98fab59ec";
	
    public static void main(String args[])
    {
       	String token="20160309151515"+TOKEN_KEY;
    	String encodeToken="F7F88D8947F59D250B6607650F8663D048B19FA790A4FB5EE096D964F683D818F277F6C4F65BE921C3D131BC7424E41914459FD557E78E8F11C9669301C1F429B65A7EEAAA8B0D066C81FABA1F71D178ECD58820745A3B22513249983FBEB9DF2393B25F80081C4AB78A56DEBF6AE6425FF382CBCA21E22D5BB6EF3CCADD8A7EA7474839C712D06877A6110D888C98BC6DDD3153E027AE2EC16A7A889DF67ADD987694C438BA1E69B30F7AD278C2E488A4CD7C8E9FF85EA49F231B4586F0D6D300C1ACADEC9875287B53D029603359F7387621B771383D6A2D279CFE99D0F8E5";

//                final String strToEncrypt = "My text to encrypt";
//                final String strPssword = "encryptor key";
              AES.setKey(TOKEY_KEY_UUID);               
              AES.encrypt(token.trim());
//                AES.setKey(TOKEY_KEY_UUID);               
//                AES.encrypt(token.trim());
                
//                System.out.println("String to Encrypt: " + strToEncrypt); 
                System.out.println("Encrypted: " + AES.getEncryptedString());
           
                final String strToDecrypt =  AES.getEncryptedString();
                AES.decrypt(strToDecrypt.trim());
               
                System.out.println("String To Decrypt : " + strToDecrypt);
                System.out.println("Decrypted : " + AES.getDecryptedString());
        
    }
     
}
