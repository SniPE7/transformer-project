/**
 * 
 */
package com.ibm.siam.am.idp.authn;

/**
 * @author zhaodonglu
 * 
 */
public enum LoginContextEventPhase {
  LOGIN, LOGOUT;

  public static LoginContextEventPhase valueByMethodName(String methodName) {
    if ("login".equals(methodName)) {
      return LOGIN;
    } else if ("logout".equals(methodName)) {
      return LOGOUT;
    } else {
      return null;
    }
  }
}
