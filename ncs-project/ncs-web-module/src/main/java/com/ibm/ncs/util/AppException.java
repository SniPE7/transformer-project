package com.ibm.ncs.util;

public class AppException extends Exception {

	/**
	 * Method 'AppException'
	 * 
	 * @param message
	 */
	public AppException(String message)
	{
		super(message);
	}

	/**
	 * Method 'AppException'
	 * 
	 * @param message
	 * @param cause
	 */
	public AppException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
