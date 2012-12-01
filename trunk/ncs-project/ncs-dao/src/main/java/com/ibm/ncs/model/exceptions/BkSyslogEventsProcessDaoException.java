package com.ibm.ncs.model.exceptions;

public class BkSyslogEventsProcessDaoException extends DaoException
{
	/**
	 * Method 'BkSyslogEventsProcessDaoException'
	 * 
	 * @param message
	 */
	public BkSyslogEventsProcessDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'BkSyslogEventsProcessDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public BkSyslogEventsProcessDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
