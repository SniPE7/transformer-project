/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

/**
 * @author zhaodonglu
 *
 */
public class SessionManagementException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -6493371114133358082L;

  /**
   * 
   */
  public SessionManagementException() {
    super();
  }

  /**
   * @param message
   */
  public SessionManagementException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public SessionManagementException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public SessionManagementException(String message, Throwable cause) {
    super(message, cause);
  }

}
