package com.sinopec.siam.am.idp.authn.service;

public class MultiplePersonFoundException extends PersonServiceException {

	/**
	 * 
	 */
  private static final long serialVersionUID = 6491088720202345489L;

	public MultiplePersonFoundException() {
		super();
	}

	public MultiplePersonFoundException(String message) {
		super(message);
	}

	public MultiplePersonFoundException(Throwable cause) {
		super(cause);
	}

	public MultiplePersonFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
