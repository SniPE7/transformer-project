/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.connector.Connector;

/**
 * @author Zhao Dong Lu
 *
 */
public class ClientException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 745579258911484352L;
  private Connector connector = null;

  /**
   * 
   */
  public ClientException() {
  }

  /**
   * @param message
   */
  public ClientException(Connector connector, String message) {
    super(message);
    this.connector  = connector;
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
  public ClientException(Connector connector, Throwable cause) {
    super(cause);
    this.connector = connector;
  }
  
  public ClientException(String m, Throwable e) {
    super(m, e);
  }

  /**
   * @return the connector
   */
  public Connector getConnector() {
    return connector;
  }

  /* (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return String.format("%s, connector: [%s]", super.getMessage(), this.connector);
  }
  
 

}
