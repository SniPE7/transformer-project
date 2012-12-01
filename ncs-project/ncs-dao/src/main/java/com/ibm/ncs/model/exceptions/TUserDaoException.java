package com.ibm.ncs.model.exceptions;

public class TUserDaoException extends DaoException
{
	/**
	 * Method 'TUserDaoException'
	 * 
	 * @param message
	 */
	public TUserDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'TUserDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public TUserDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
