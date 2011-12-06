/**
 * 
 */
package com.ibm.tivoli.cmcc.web;

/**
 * @author zhaodonglu
 *
 */
public class WebPageException extends Exception {

  /**
   * 
   */
  public WebPageException() {
    super();
  }

  /**
   * @param arg0
   */
  public WebPageException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public WebPageException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public WebPageException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
