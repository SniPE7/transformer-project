package com.sinopec.siam.am.idp.authn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * 类（接口）继承、实现、功能等描述
 * 
 * @author ZhouTengfei
 * @since 2012-7-31 下午12:36:49
 */

public class AuthencationUtil {

  /**
   * Authenticate the user making the request.
   * 
   * @param requestedMethod
   * @param supportedLoginHandlers
   * 
   * @return authenticate Types.
   */
  public static String getAuthenType(String requestedMethod, Map<String, LoginHandler> supportedLoginHandlers) {
    StringBuffer buffer = new StringBuffer();
    if (requestedMethod != null && !requestedMethod.isEmpty()) {
      Iterator<Entry<String, LoginHandler>> supportedLoginHandlerItr = supportedLoginHandlers.entrySet().iterator();
      Entry<String, LoginHandler> supportedLoginHandlerEntry;
      Entry<String, LoginHandler> currentLoginHandlerEntry = null;
      Map<String, LoginHandler> loginHandlers = new HashMap<String, LoginHandler>();
      while (supportedLoginHandlerItr.hasNext()) {
        supportedLoginHandlerEntry = supportedLoginHandlerItr.next();
        if (!supportedLoginHandlerEntry.getKey().equals(AuthenticationEngine.PREVIOUS_SESSION_AUTHN_CTX)) {
          if (requestedMethod != supportedLoginHandlerEntry.getKey()
              && !requestedMethod.equals(supportedLoginHandlerEntry.getKey())) {
            loginHandlers.put(supportedLoginHandlerEntry.getKey(), supportedLoginHandlerEntry.getValue());
          } else {
            currentLoginHandlerEntry = supportedLoginHandlerEntry;
            buffer.append(currentLoginHandlerEntry.getKey().replace(":", "_")).append(",");
          }
        }
      }

      supportedLoginHandlerItr = loginHandlers.entrySet().iterator();
      if (currentLoginHandlerEntry != null) {
        while (supportedLoginHandlerItr.hasNext()) {
          supportedLoginHandlerEntry = supportedLoginHandlerItr.next();
          if (supportedLoginHandlerEntry.getValue().getAuthenticationLevel() >= currentLoginHandlerEntry.getValue()
              .getAuthenticationLevel()) {
            buffer.append(supportedLoginHandlerEntry.getKey().replace(":", "_")).append(",");
          }
        }
      }
    }
    return buffer.toString();
  }

}
