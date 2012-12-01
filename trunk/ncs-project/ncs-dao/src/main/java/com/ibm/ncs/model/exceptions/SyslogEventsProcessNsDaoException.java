package com.ibm.ncs.model.exceptions;

public class SyslogEventsProcessNsDaoException extends DaoException
{
	/**
	 * Method 'SyslogEventsProcessNsDaoException'
	 * 
	 * @param message
	 */
	public SyslogEventsProcessNsDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'SyslogEventsProcessNsDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public SyslogEventsProcessNsDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
