package com.sinopec.siam.utils;

import java.text.MessageFormat;

public class StringFormatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String userFilter = "(&(|(uid={0})(badgeid={0}))(objectclass=inetOrgPerson))";
		String userName = "zhangmin";
		
	    String filter = MessageFormat.format(userFilter, userName);
	    
	    System.out.println("filter:" + filter);

	}

}
