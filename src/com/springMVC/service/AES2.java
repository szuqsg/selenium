package com.springMVC.service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.xmlbeans.impl.util.Base64;

//import org.apache.tomcat.util.codec.binary.Base64;

import org.apache.commons.codec.binary.Base64;

//import org.apache.catalina.util.Base64;

//import com.sun.xml.internal.messaging.saaj.util.Base64;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

//import org.springframework.security.crypto.codec.Base64;

public class AES2 {
	private final String characterEncoding = "UTF-8";
    private final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private final String aesEncryptionAlgorithm = "AES";
 
    public  byte[] decrypt(byte[] cipherText, byte[] key, byte [] initialVector) throws Exception
    {
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        int il=cipherText.length;
        System.out.println("il : " + il);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }
 
    public byte[] encrypt(byte[] plainText, byte[] key, byte [] initialVector) throws Exception
    {
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }
 
    private byte[] getKeyBytes(String key) throws  Exception
    {
        byte[] keyBytes= new byte[16];
        byte[] parameterKeyBytes= key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }
 
    /// <summary>
    /// Encrypts plaintext using AES 128bit key and a Chain Block Cipher and returns a base64 encoded string
    /// </summary>
    /// <param name="plainText">Plain text to encrypt</param>
    /// <param name="key">Secret key</param>
    /// <returns>Base64 encoded string</returns>
    public String encrypt(String plainText, String key) throws Exception
    {
        byte[] plainTextbytes = plainText.getBytes(characterEncoding);
        byte[] keyBytes = getKeyBytes(key);
        byte[] encryptByte=encrypt(plainTextbytes,keyBytes, keyBytes);
        
        return new String(Base64.encodeBase64(encryptByte));
//        return Base64.encode(encryptByte).toString();
    }
 
    /// <summary>
    /// Decrypts a base64 encoded string using the given key (AES 128bit key and a Chain Block Cipher)
    /// </summary>
    /// <param name="encryptedText">Base64 Encoded String</param>
    /// <param name="key">Secret Key</param>
    /// <returns>Decrypted String</returns>
    public String decrypt(String encryptedText, String key) throws Exception
    {
        byte[] cipheredBytes = Base64.decodeBase64(encryptedText.getBytes());
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), characterEncoding);
    }
    
    private static String TOKEN_KEY="i37iq3nlu3a4b6v9yrjxrjeit3qhptg534v8u54hj40joo0hoo6lzb60s7otiamm32j"
			+ "k60y5hg89jr0u3pwu816868bi23x5r6nmtjcybepum39l45tu3axy5dwz9rb8dol2lewvxgwl6s91h50334zjj3du77xnr7yzkl98fb5feaj3055bu1cyf6ixsdnplw5br4ri";
	
	private static String TOKEY_KEY_UUID = "ceeb83109a59489abff4e3b98fab59ec";
	
    public static void main(String args[]) throws Exception
    {
       	String token="20160309111213"+TOKEN_KEY;
    	String encodeToken="4XWpthcCDuPvkBOh31wi464SjjV3iIT5EosZFkajLlkrPXkT6LCDTWHoPqpdv9btUF6JhcxKZNft+ZumGe1LOS6BOsli80s8P/XH+fqGNmfUTU1mCkdP2EBKEg9rWM4TkQJQ8gmgIB8jl4A5ns1tSITmcX7U4HmwvXPLUoTRZdrZrgyYBGUqAI1hUEgeHnZP/AbtjmJVE7phr/Xn9wJUGNUSibfdfOjHNZpI/iupgfyWX58ILVjsTU8/3v7hHRRfxPRsunJ2vUCyiWBQs1srt5WGOFUUPi0JcZHoMiKh9d0+Y2JeOyMerRgR8tV9M9WT";

        AES2 aes2=new AES2();    
        System.out.println("token length: " + encodeToken.length());
        String encryptedString=aes2.encrypt(token,TOKEY_KEY_UUID);
        System.out.println("Encrypted: " + encryptedString);
   
        String decryptedString=aes2.decrypt(encodeToken,TOKEY_KEY_UUID);
//
        System.out.println("Decrypted : " + decryptedString);
        
    }
}
