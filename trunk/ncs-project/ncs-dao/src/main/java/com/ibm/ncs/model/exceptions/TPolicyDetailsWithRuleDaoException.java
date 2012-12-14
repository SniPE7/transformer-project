package com.ibm.ncs.model.exceptions;

public class TPolicyDetailsWithRuleDaoException extends DaoException {

	/**
	 * 
	 */
  private static final long serialVersionUID = -7808490737817173628L;

  public TPolicyDetailsWithRuleDaoException(String message) {
	  super(message);
  }

	public TPolicyDetailsWithRuleDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
