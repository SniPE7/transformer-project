/**
 * 
 */
package com.ibm.siam.am.idp.authn;

/**
 * @author zhaodonglu
 * 
 */
public enum LoginModuleEventPhase {
  LOGIN, COMMIT, ABORT, LOGOUT;

  public static LoginModuleEventPhase valueByMethodName(String methodName) {
    if ("login".equals(methodName)) {
      return LOGIN;
    } else if ("commit".equals(methodName)) {
      return COMMIT;
    } else if ("abort".equals(methodName)) {
      return COMMIT;
    } else if ("logout".equals(methodName)) {
      return LOGOUT;
    } else {
      return null;
    }
  }
}
