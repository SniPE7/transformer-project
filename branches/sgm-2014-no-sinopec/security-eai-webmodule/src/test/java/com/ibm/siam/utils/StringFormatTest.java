package com.ibm.siam.utils;

import java.text.MessageFormat;

public class StringFormatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String cardUid = "9E840F0A";

		int b1 = Integer.parseInt(cardUid.substring(0, 2), 16);
		
		int b2 = Integer.parseInt(cardUid.substring(2, 4), 16);
		int b3 = Integer.parseInt(cardUid.substring(4, 6), 16);
		int b4 = Integer.parseInt(cardUid.substring(6, 8), 16);
		int xor = b1^b2^b3^b4;
		String xorStr = Integer.toHexString(xor).toUpperCase();
		if (xorStr.length() < 2) {
			xorStr = "0" + xorStr;
		}
		
		
		String userFilter = "(&(|(uid={0})(badgeid={0}))(objectclass=inetOrgPerson))";
		String userName = "zhangmin";
		
	    String filter = MessageFormat.format(userFilter, userName);
	    
	    System.out.println("filter:" + filter);

	}

}
