package com.ibm.ncs.model.exceptions;

public class SyslogEventsProcessDaoException extends DaoException
{
	/**
	 * Method 'SyslogEventsProcessDaoException'
	 * 
	 * @param message
	 */
	public SyslogEventsProcessDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'SyslogEventsProcessDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public SyslogEventsProcessDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
