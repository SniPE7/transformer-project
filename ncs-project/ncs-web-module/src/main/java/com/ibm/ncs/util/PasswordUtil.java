/**
 * 
 */
package com.ibm.ncs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 * 
 */
public class PasswordUtil {
	
	private static Log log = LogFactory.getLog(PasswordUtil.class);

	/**
	 * 
	 */
	public PasswordUtil() {
		super();
	}

	public static String encode(String password) {
		try {
	    byte[] bt = password.getBytes();
	    String encName = "SHA1";
	    MessageDigest md = MessageDigest.getInstance(encName);
	    md.update(bt);
	    String strDes = Base64Encode.encode(md.digest());
	    return String.format("{%s}%s", encName, strDes);
    } catch (NoSuchAlgorithmException e) {
	    log.error("Failure encode password", e);
	    return password;
    }
	}

	public static boolean verify(String password, String shadow) {
		if (password == null || shadow == null) {
			return false;
		}
		
		if (shadow.startsWith("{SHA1}")) {
			return shadow.equals(encode(password));
		}
		
		if (password.equals(shadow)) {
			 return true;
		}
    return (shadow.equals(Base64Encode.encode(password.getBytes())));
	}

}
