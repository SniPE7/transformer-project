/**
 * 
 */
package com.ibm.tivoli.cmcc.module;

import java.security.Principal;

/**
 * @author zhaodonglu
 *
 */
public interface PrincipalAware {

  public Principal getPrincipal();
}
