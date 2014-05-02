package com.ibm.siam.am.idp.authn.util.dyncpwd;

import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.engines.DESedeWrapEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class ThreeDes {

	public static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58,
			(byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
			(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40,
			0x36, (byte) 0xE2 };

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {

			byte[] iv = new byte[] { 0x5d, (byte) 0xd4, (byte) 0xcb,
					(byte) 0xfc, (byte) 0x96, (byte) 0xf5, 0x45, 0x3b };

			Wrapper wrapper = new DESedeWrapEngine();
			wrapper.init(true, new ParametersWithIV(new KeyParameter(keybyte),
					iv));
			return wrapper.wrap(src, 0, src.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			Wrapper wrapper = new DESedeWrapEngine();
			wrapper.init(false, new KeyParameter(keybyte));
			return wrapper.unwrap(src, 0, src.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF);
			if (stmp.length() == 1) {
				hs += "0" + stmp;
			} else {
				hs += stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) {
		//String szSrc = "I000001,-3,6,22,9,114,-44,-2,100";
//		String szSrc = "420421I888,18,70,-55,-91,40,-54,-25,-2,0";
		String srcstr="420421I8";
		byte [] src=srcstr.getBytes();
//	    byte [] src= new byte []{
//	    		'4','2','0','4','2','1','I','8'	
//	    };
		System.out.println("原始字符串[" + new String(src)+"]"+src.length);
		byte[] encoded = encryptMode(keyBytes, src);
		System.out.println("加密前的字符串:" +  byte2hex(encoded));
		byte2hex(encoded);
		
		byte[] decoded = decryptMode(keyBytes, encoded);
		System.out.println("解密后的字符串[" + new String(decoded)+"]");
		for(int i=0;i<src.length;i++){
			if( decoded[i] != src[i]){
				System.out.println("["+src[i]+"]:["+decoded[i]+"]");
			}
		}
	}
}
