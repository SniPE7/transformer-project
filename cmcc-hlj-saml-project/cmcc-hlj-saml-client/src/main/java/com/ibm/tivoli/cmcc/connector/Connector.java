/**
 * 
 */
package com.ibm.tivoli.cmcc.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhaodonglu
 *
 */
public interface Connector {
  
  public void open() throws Exception;
  
  public void release();
  
  public OutputStream getOutput() throws IOException;
  
  public InputStream getInput() throws IOException;

}
