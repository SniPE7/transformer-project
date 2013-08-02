package com.sinopec.siam.am.idp.management;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.util.storage.StorageService;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sinopec.siam.am.idp.authn.IdPMonitorEvent;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;
import com.sinopec.siam.am.idp.authn.provider.cert.ClientCertCallbackHandler;
import com.sinopec.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;

import edu.internet2.middleware.shibboleth.common.session.SessionManager;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.session.Session;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * IdP monitor.
 * 
 * @author Booker
 * 
 */
@ManagedResource(objectName = "SIAM:type=IDP,name=IdPMonitor", description = "IdPMonitor MBean", log = true, logFile = "jmx.log")
public class IdPMonitor {
  private static Log log = LogFactory.getLog(IdPMonitor.class);

  /**
   * 记录成功。
   */
  private Map<String, Map<String, Integer>> authenSuccessRecord = new HashMap<String, Map<String, Integer>>();
  /**
   * 记录失败。
   */
  private Map<String, Map<String, Integer>> authenFailedRecord = new HashMap<String, Map<String, Integer>>();
  /**
   * Session 管理。
   */
  private SessionManager<Session> sessionManager;

  /**
   * @param event
   */
  public void recognizeAndRecord(StorageService<String, LoginContextEntry> storageService, IdPMonitorEvent event) {
    String spEntityID = null;
    String authMethod = null;
    HttpServletRequest request = null;
    RequestCallback requestCallback = new RequestCallback();
    Callback[] callbacks = new Callback[1];
    callbacks[0] = requestCallback;
    CallbackHandler callbackHandler = event.getCallbackHandler();

    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.info("Could not get callback Handler, please check configuration.");
    } catch (UnsupportedCallbackException e) {
      log.info("Could not support Callback, please check configuration.");
    }

    if (callbackHandler instanceof TAMCallbackHandler) {
      request = requestCallback.getRequest();
    }
    if (callbackHandler instanceof ClientCertCallbackHandler) {
      request = requestCallback.getRequest();
    }

    edu.internet2.middleware.shibboleth.idp.authn.LoginContext loginContext;
    try {
      loginContext = HttpServletHelper.getLoginContext(storageService, request.getSession().getServletContext(),
          request);
      spEntityID = loginContext.getRelyingPartyId();
      authMethod = loginContext.getAttemptedAuthnMethod();
    } catch (Exception e) {
      log.info("Could not get loginContext, please check configuration.");
    }

    if (event.getStatus().isSuccess()) {
      authenSuccess(spEntityID, authMethod);
    } else {
      authenFailed(spEntityID, authMethod);
    }
  }

  /**
   * 认证成功记录。
   * 
   * @param spId
   * @param authenMethod
   */
  public void authenSuccess(String spId, String authenMethod) {
    if (!authenSuccessRecord.containsKey(spId)) {
      authenSuccessRecord.put(spId, new HashMap<String, Integer>());
    }

    if (!authenSuccessRecord.get(spId).containsKey(authenMethod)) {
      authenSuccessRecord.get(spId).put(authenMethod, 1);
    } else {
      authenSuccessRecord.get(spId).put(authenMethod, authenSuccessRecord.get(spId).get(authenMethod) + 1);
    }
  }

  /**
   * 认证失败记录。
   * 
   * @param spId
   * @param authenMethod
   */
  public void authenFailed(String spId, String authenMethod) {
    if (!authenFailedRecord.containsKey(spId)) {
      authenFailedRecord.put(spId, new HashMap<String, Integer>());
    }

    if (!authenFailedRecord.get(spId).containsKey(authenMethod)) {
      authenFailedRecord.get(spId).put(authenMethod, 1);
    } else {
      authenFailedRecord.get(spId).put(authenMethod, authenFailedRecord.get(spId).get(authenMethod) + 1);
    }
  }

  /**
   * 成功次数。
   * 
   * @param spId
   * @param authMethod
   * @return
   */
  @ManagedOperation(description = "Return count of authenticate success by spId and authMethod.")
  public int getCountOfAuthenSuccess(String spId, String authMethod) {
    if (authenSuccessRecord.containsKey(spId)) {
      if (authenSuccessRecord.get(spId).containsKey(authMethod)) {
        return authenSuccessRecord.get(spId).get(authMethod);
      }
    }
    return 0;
  }

  /**
   * 失败次数。
   * 
   * @param spId
   * @param authMethod
   * @return
   */
  @ManagedOperation(description = "Return count of authenicate failed by spId and authMethod.")
  public int getCountOfAuthenFailed(String spId, String authMethod) {
    if (authenFailedRecord.containsKey(spId)) {
      if (authenFailedRecord.get(spId).containsKey(authMethod)) {
        return authenFailedRecord.get(spId).get(authMethod);
      }
    }
    return 0;
  }

  /**
   * 认证成功Map。
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  @ManagedOperation(description = "Return map of authenticate success by AuthMethod.")
  public Map<String, Integer> getCountOfAuthenSuccessByAuthMethod() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    int count = 0;
    String method = "";
    Entry<String, Map<String, Integer>> spEntry;
    Map<String, Integer> authMethodMap;
    Entry<String, Integer> authMethodEntry;
    if (!authenSuccessRecord.keySet().isEmpty()) {
      for (Iterator<?> it = authenSuccessRecord.entrySet().iterator(); it.hasNext();) {
        spEntry = (Entry<String, Map<String, Integer>>) it.next();
        authMethodMap = (Map<String, Integer>) spEntry.getValue();
        for (Iterator<?> it2 = authMethodMap.entrySet().iterator(); it2.hasNext();) {
          authMethodEntry = (Entry<String, Integer>) it2.next();
          method = authMethodEntry.getKey();
          count = authMethodEntry.getValue();
          if (map.containsKey(method)) {
            count += map.get(method);
          }
          map.put(method, count);
        }
      }
    }
    return map;
  }

  /**
   * 认证失败Map。
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  @ManagedOperation(description = "Return map of authenticate failed by AuthMethod.")
  public Map<String, Integer> getCountOfAuthenFailedByAuthMethod() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    int count = 0;
    String method = "";
    Entry<String, Map<String, Integer>> spEntry;
    Map<String, Integer> authMethodMap;
    Entry<String, Integer> authMethodEntry;
    if (!authenFailedRecord.keySet().isEmpty()) {
      for (Iterator<?> it = authenFailedRecord.entrySet().iterator(); it.hasNext();) {
        spEntry = (Entry<String, Map<String, Integer>>) it.next();
        authMethodMap = (Map<String, Integer>) spEntry.getValue();
        for (Iterator<?> it2 = authMethodMap.entrySet().iterator(); it2.hasNext();) {
          authMethodEntry = (Entry<String, Integer>) it2.next();
          method = authMethodEntry.getKey();
          count = authMethodEntry.getValue();
          if (map.containsKey(method)) {
            count += map.get(method);
          }
          map.put(method, count);
        }
      }
    }
    return map;
  }

  /**
   * 认证成功Map。
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  @ManagedOperation(description = "Return map of authenticate success by Sp.")
  public Map<String, Integer> getCountOfAuthenSuccessBySp() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    int count = 0;
    String sp = "";
    Entry<String, Map<String, Integer>> spEntry;
    Map<String, Integer> authMethodMap;
    Entry<String, Integer> authMethodEntry;
    if (!authenSuccessRecord.keySet().isEmpty()) {
      for (Iterator<?> it = authenSuccessRecord.entrySet().iterator(); it.hasNext();) {
        spEntry = (Entry<String, Map<String, Integer>>) it.next();
        sp = spEntry.getKey();
        authMethodMap = (Map<String, Integer>) spEntry.getValue();
        for (Iterator<?> it2 = authMethodMap.entrySet().iterator(); it2.hasNext();) {
          authMethodEntry = (Entry<String, Integer>) it2.next();
          count = authMethodEntry.getValue();
          if (map.containsKey(sp)) {
            count += map.get(sp);
          }
          map.put(sp, count);
        }
      }
    }
    return map;
  }

  /**
   * 认证失败Map。
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  @ManagedOperation(description = "Return map of authenticate failed by Sp.")
  public Map<String, Integer> getCountOfAuthenFailedBySp() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    int count = 0;
    String sp = "";
    Entry<String, Map<String, Integer>> spEntry;
    Map<String, Integer> authMethodMap;
    Entry<String, Integer> authMethodEntry;
    if (!authenFailedRecord.keySet().isEmpty()) {
      for (Iterator<?> it = authenFailedRecord.entrySet().iterator(); it.hasNext();) {
        spEntry = (Entry<String, Map<String, Integer>>) it.next();
        sp = spEntry.getKey();
        authMethodMap = (Map<String, Integer>) spEntry.getValue();
        for (Iterator<?> it2 = authMethodMap.entrySet().iterator(); it2.hasNext();) {
          authMethodEntry = (Entry<String, Integer>) it2.next();
          count = authMethodEntry.getValue();
          if (map.containsKey(sp)) {
            count += map.get(sp);
          }
          map.put(sp, count);
        }
      }
    }
    return map;
  }

  /**
   * 活跃用户。
   * 
   * @return
   */
  @ManagedOperation(description = "Return count of active user.")
  public int getCountOfActiveUser() {
    return sessionManager.getSessionCount();
  }

  /**
   * @return the sessionManager
   */
  public SessionManager<Session> getSessionManager() {
    return sessionManager;
  }

  /**
   * @param sessionManager
   *          the sessionManager to set
   */
  public void setSessionManager(SessionManager<Session> sessionManager) {
    this.sessionManager = sessionManager;
  }
}
