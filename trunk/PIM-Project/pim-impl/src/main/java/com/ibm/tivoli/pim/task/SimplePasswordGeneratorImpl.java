/**
 * 
 */
package com.ibm.tivoli.pim.task;

/**
 * @author Administrator
 *
 */
public class SimplePasswordGeneratorImpl implements PasswordGenerator {

  /**
   * 
   */
  public SimplePasswordGeneratorImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.task.PasswordGenerator#generate()
   */
  public String generate() {
    return "passw0rd";
  }

}
