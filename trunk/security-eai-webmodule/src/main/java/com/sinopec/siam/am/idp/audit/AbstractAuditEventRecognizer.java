/**
 * 
 */
package com.sinopec.siam.am.idp.audit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.util.storage.StorageService;

import com.sinopec.siam.am.idp.authn.AuditableSpringLoginContext;
import com.sinopec.siam.am.idp.authn.LoginContextEvent;
import com.sinopec.siam.am.idp.authn.LoginContextStorageManagerAware;
import com.sinopec.siam.am.idp.authn.LoginModuleEvent;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;
import com.sinopec.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;
import com.sinopec.siam.audit.model.W7Event;
import com.sinopec.siam.audit.model.W7OnWhat;
import com.sinopec.siam.audit.model.W7What;
import com.sinopec.siam.audit.model.W7Where;
import com.sinopec.siam.audit.model.W7Who;
import com.sinopec.siam.utils.FileOrClasspathInputStream;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * @author zhaodonglu
 * 
 */
public abstract class AbstractAuditEventRecognizer implements AuditEventRecognizer, LoginContextStorageManagerAware {

  private static Log log = LogFactory.getLog(AbstractAuditEventRecognizer.class);

  private static Log w7Log = LogFactory.getLog("IDP-Login-AUDIT-Logger-W7");
  
  private String logModuleNameMappingPath = null;

  /**
   * Storage service used to store {@link LoginContext}s while authentication is
   * in progress.
   */
  private StorageService<String, LoginContextEntry> storageService;

  /**
   * 
   */
  public AbstractAuditEventRecognizer() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.sinopec.siam.am.idp.authn.LoginContextStorageManagerAware#setStorageService
   * (org.opensaml.util.storage.StorageService)
   */
  public void setStorageService(StorageService<String, LoginContextEntry> storageService) {
    this.storageService = storageService;
  }

  /**
   * @param verbName
   * @param success
   * @param callbackHandler
   * @return
   */
  private W7Event createBasicW7Event(String verbName, boolean success, CallbackHandler callbackHandler, String verbNoun) {
    // 时间
    Date when = new Date();
    // 账号
    W7Who who = new W7Who();
    // 动作，成功/失败
    W7What what = new W7What();
    // 操作发生IDP ID
    W7OnWhat onWhat = new W7OnWhat();
    // 客户端浏览器IP
    W7Where where = new W7Where();
    // SP地址
    W7Where fromWhere = new W7Where();
    // IDP地址
    W7Where toWhere = new W7Where();

    String IDP_entityID = null;
    String uid = null;
    HttpServletRequest request = null;
    String loginAuthnMethod = null;
    

    RequestCallback requestCallback = new RequestCallback();
    NameCallback ncb = new NameCallback("User Name: ");
    Callback[] callbacks = new Callback[2];
    callbacks[0] = requestCallback;
    callbacks[1] = ncb;
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.info("Could not get callback Handler, please check configuration.");
    } catch (UnsupportedCallbackException e) {
      log.info("Could not support Callback, please check configuration.");
    }

    if (callbackHandler instanceof TAMCallbackHandler) {
      request = requestCallback.getRequest();
      uid = ncb.getName();
    }

    IDP_entityID = request.getServerName();

    who.setAuthenName(uid);
    who.setAuthenType(loginAuthnMethod);

    where.setType("IP");
    where.setName(request.getRemoteAddr());

    toWhere.setType("IDP");
    toWhere.setName(IDP_entityID);

    what.setVerb(verbName);
    what.setNoun(verbNoun);
    what.setSuccess(Boolean.toString(success));

    onWhat.setType("IDP");
    onWhat.setName(IDP_entityID);

    W7Event w7event = new W7Event();
    w7event.setWhenTime(when);
    w7event.setWho(who);
    w7event.setWhat(what);
    w7event.setOnWhat(onWhat);
    w7event.setWhere(where);
    w7event.setWhereFrom(fromWhere);
    w7event.setWhereTo(toWhere);
    return w7event;
  }

  /**
   * @param event
   * @return
   */
  protected W7Event createBasicW7Event(LoginModuleEvent event) {
    String verbName = event.getLoginModuleEntry().getLoginModuleName();
    boolean success = event.getStatus().isSuccess();
    String verbNoun = event.getAuditableClassHashCode();
    if(success==true){
      event.setLogLevel("3");
    }else{
      event.setLogLevel("2");
    }
    CallbackHandler callbackHandler = event.getCallbackHandler();
    return createBasicW7Event(verbName, success, callbackHandler,verbNoun);
  }

  /**
   * @param event
   * @return
   */
  protected W7Event createBasicW7Event(LoginContextEvent event) {
    String loginContextName = "";
    if (event.getLoginContext() instanceof AuditableSpringLoginContext) {
      loginContextName = ((AuditableSpringLoginContext) event.getLoginContext()).getLoginContextName();
    }

    String verbName = loginContextName;
    verbName = readValue(verbName);
    boolean success = event.getStatus().isSuccess();
    String verbNoun = event.getAuditableClassHashCode();
    event.setLogLevel("1");
    CallbackHandler callbackHandler = event.getCallbackHandler();
    return createBasicW7Event(verbName, success, callbackHandler,verbNoun);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.sinopec.siam.am.idp.audit.AuditEventRecognizer#recognizeAndRecord(com
   * .sinopec.siam.am.idp.authn.LoginModuleEvent)
   */
  public void recognizeAndRecord(LoginModuleEvent event) {
    if (this.isAuditEvent(event)) {
      W7Event w7Event = this.createBasicW7Event(event);
      this.completeAuditEvent(event, w7Event);
      if(event.getLogLevel().equals("2")){
        w7Log.info(w7Event.toXML().toString().trim());
      }else if(event.getLogLevel().equals("3")){
        w7Log.debug(w7Event.toXML().toString().trim());
      }else if(event.getLogLevel().equals("1")){
        w7Log.error(w7Event.toXML().toString().trim());
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.sinopec.siam.am.idp.audit.AuditEventRecognizer#recognizeAndRecord(com
   * .sinopec.siam.am.idp.authn.LoginContextEvent)
   */
  public void recognizeAndRecord(LoginContextEvent event) {
    if (this.isAuditEvent(event)) {
      W7Event w7Event = this.createBasicW7Event(event);
      this.completeAuditEvent(event, w7Event);
      if(event.getLogLevel().equals("1")){
        w7Log.error(w7Event.toXML().toString().trim());
      }else if(event.getLogLevel().equals("2")){
        w7Log.info(w7Event.toXML().toString().trim());
      }else if(event.getLogLevel().equals("3")){
        w7Log.debug(w7Event.toXML().toString().trim());
      }
    }
  }

  /**
   * @param event
   * @return
   */
  protected abstract boolean isAuditEvent(LoginModuleEvent event);

  /**
   * @param event
   * @return
   */
  protected abstract boolean isAuditEvent(LoginContextEvent event);

  /**
   * @param w7Event
   */
  protected abstract void completeAuditEvent(LoginModuleEvent event, W7Event w7Event);

  /**
   * @param w7Event
   */
  protected abstract void completeAuditEvent(LoginContextEvent event, W7Event w7Event);

  public String readValue(String key) {
    Properties props = new Properties();
    try {
      InputStream in = new BufferedInputStream(new FileOrClasspathInputStream(getLogModuleNameMappingPath()));
      props.load(in);
      String value = props.getProperty(key);
      return value;
    } catch (Exception e) {
      log.info("Could not get logModuleMappingName, please check configuration.");
      return null;
    }
  }

  public String getLogModuleNameMappingPath() {
    return logModuleNameMappingPath;
  }

  public void setLogModuleNameMappingPath(String logModuleNameMappingPath) {
    this.logModuleNameMappingPath = logModuleNameMappingPath;
  }

}
