package com.sinopec.siam.am.idp.authn.service;

public class PersonServiceException extends Exception {

	/**
	 * 
	 */
  private static final long serialVersionUID = -293406250769147775L;

	public PersonServiceException() {
		super();
	}

	public PersonServiceException(String message) {
		super(message);
	}

	public PersonServiceException(Throwable cause) {
		super(cause);
	}

	public PersonServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
