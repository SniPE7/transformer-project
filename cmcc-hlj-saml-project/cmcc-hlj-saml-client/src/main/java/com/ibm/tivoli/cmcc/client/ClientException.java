/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

/**
 * @author Zhao Dong Lu
 *
 */
public class ClientException extends Exception {

  /**
   * 
   */
  public ClientException() {
  }

  /**
   * @param message
   */
  public ClientException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public ClientException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }

}
