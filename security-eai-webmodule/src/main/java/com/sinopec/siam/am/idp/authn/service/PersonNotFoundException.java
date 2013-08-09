package com.sinopec.siam.am.idp.authn.service;

public class PersonNotFoundException extends PersonServiceException {

	/**
	 * 
	 */
  private static final long serialVersionUID = -5741671937877717690L;

	public PersonNotFoundException() {
		super();
	}

	public PersonNotFoundException(String message) {
		super(message);
	}

	public PersonNotFoundException(Throwable cause) {
		super(cause);
	}

	public PersonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
