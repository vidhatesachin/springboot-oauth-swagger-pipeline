package com.medinosys.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	/**
	   * Method to auto-generate password
	   * @return String 
	   */
	public static String generatePassword() {  
		 final String ALLOWED_PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$%*";  
	      SecureRandom randomNo = new SecureRandom();  
	      StringBuffer result = new StringBuffer();  
	     
	      for( int i=0; i<8; i++ ){  
	          result.append( ALLOWED_PASSWORD_CHARS.charAt(randomNo.nextInt( ALLOWED_PASSWORD_CHARS.length() ) ) );  
	      }  
	      return result.toString();  
	}
 
	/**
	 * getHashString()
	 * @param message: String that needs to be hashed
	 * @param algorithm:  algorithm can be "MD5", "SHA-1", "SHA-256"
	 * @return
	 */
	public static String getHashString(String message, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
			return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			throw new RuntimeException("Could not generate hash from String", ex);
		}
	}
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
	
	public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
	  
}
