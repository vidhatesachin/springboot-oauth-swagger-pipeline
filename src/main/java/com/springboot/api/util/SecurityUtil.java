package com.springboot.api.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class SecurityUtil {
    
	public final String AES = "AES";
    
	@Value("${api.sec.key}")
	private String aesKey;
    
    private String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    
    public String encryptPass(String password) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		byte[] bytekey = hexStringToByteArray(aesKey);
		SecretKeySpec sks = new SecretKeySpec(bytekey, this.AES);
		Cipher cipher = Cipher.getInstance(this.AES);
		cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
		byte[] encrypted = cipher.doFinal(password.getBytes());
		return byteArrayToHexString(encrypted);
    }
    
    public String decryptPass(String encPassword) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
    	byte[] bytekey = hexStringToByteArray(aesKey);
        SecretKeySpec sks = new SecretKeySpec(bytekey, this.AES);
        Cipher cipher = Cipher.getInstance(this.AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(encPassword));
		return new String(decrypted);
    }
    
    public static void main(String args[]) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException { 
    	 Scanner scanner = new Scanner(System.in);
         System.out.println("Please Enter Plain Text Password:");
         String password = scanner.next();
         SecurityUtil su = new SecurityUtil();
         String encryptedpwd = su.encryptPass(password);
         System.out.println("****************  Encrypted Password  ****************");
         System.out.println(encryptedpwd);
         
         String decryptedpwd = su.decryptPass(encryptedpwd);
         System.out.println("****************  Decrypted Password  ****************");
         System.out.println(decryptedpwd);
    }
}
