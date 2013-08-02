/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module.itim;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.ws.exceptions.WSInvalidLoginException;
import com.ibm.itim.ws.exceptions.WSLoginServiceException;
import com.ibm.itim.ws.model.WSChallengeResponseInfo;
import com.ibm.itim.ws.model.WSLocale;
import com.ibm.itim.ws.model.WSPerson;
import com.ibm.itim.ws.model.WSSession;
import com.ibm.itim.ws.model.WSSystemUser;
import com.ibm.itim.ws.services.WSItimService;
import com.ibm.itim.ws.services.facade.ITIMWebServiceFactory;
import com.sinopec.siam.am.idp.authn.module.AbstractLoginModule;

/**
 * @author zhaodonglu
 * 
 */
public class ITIMLoginModule extends AbstractLoginModule {
  
  private Log log = LogFactory.getLog(ITIMLoginModule.class);

  private String soapEndpoint = null;

  /**
   * 
   */
  public ITIMLoginModule() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.sinopec.siam.am.idp.authn.module.AbstractLoginModule#initialize(javax
   * .security.auth.Subject, javax.security.auth.callback.CallbackHandler,
   * java.util.Map, java.util.Map)
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.soapEndpoint = (String) options.get("soapEndpoint");
    if (this.soapEndpoint == null || "".equals(this.soapEndpoint)) {
      throw new RuntimeException("soapEndpoint is null");
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#login()
   */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error(String.format("No CallbackHandler for [%s]", this.getClass().getSimpleName()));
      return false;
    }

    // 客户端提交证书
    ITIMCallback callback = new ITIMCallback();
    Callback[] callbacks = new Callback[] { new ITIMCallback() };
    callbacks[0] = callback;
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    try {
      ITIMWebServiceFactory webServiceFactory = new ITIMWebServiceFactory(this.soapEndpoint);
      WSItimService wsItimService = webServiceFactory.getWSItimService();
      WSSession session = null;
      if (callback.getChallendeReponseInfo() != null && callback.getChallendeReponseInfo().size() > 0) {
        // Do challenge mode login
        session = wsItimService.lostPasswordLoginDirectEntry(callback.getPrincipal(), callback.getChallendeReponseInfo().toArray(new WSChallengeResponseInfo[0]), new WSLocale(callback.getLocale().getCountry(), callback.getLocale().getLanguage(), callback.getLocale().getVariant()));
        //webServiceFactory.getWSSessionService().logout(session);
      } else if (callback.getCredential() != null && callback.getCredential().trim().length() > 0) {
        // Do username/password login
        session = wsItimService.login(callback.getPrincipal(), callback.getCredential());
        //webServiceFactory.getWSSessionService().logout(session);
      }
      
      // Pass!, Get more information for current user.
      ItimPrincipal principal = new ItimPrincipal();
      principal.setName(callback.getPrincipal());
      WSPerson wsPerson = wsItimService.getPrincipalPerson(session);
      WSSystemUser wsSysUser = wsItimService.getSystemUser(session);
      // erchangepswdrequired
      principals.add(principal);

      authenticated = true;
      return true;
    } catch (WSInvalidLoginException e) {
      String faultString = e.getFaultString();
      log.error(String.format("failure to login with callback: [%s], cause: %s", callback, faultString), e);
      // Password expired or be reseted: "com.ibm.itim.apps.ejb.home.HomeBean.PASSWORD_EXPIRED"
      if (faultString != null && faultString.indexOf("PASSWORD_EXPIRED") >= 0) {
         throw new CredentialExpiredException(String.format("User:[%s] password expired!", callback.getPrincipal()));
      } else if (faultString != null && faultString.indexOf("The information used to login is not correct") >= 0) {
        // Wrong password ot uid: "The information used to login is not correct"
        throw new FailedLoginException(String.format("User:[%s] password expired!", callback.getPrincipal()));
      }
      throw new LoginException(String.format("failure to login with callback: [%s], cause: %s", callback, faultString));
    } catch (WSLoginServiceException e) {
      log.error(String.format("failure to login with callback: [%s], cause: %s", callback, e.getFaultString()), e);
      throw new LoginException(String.format("failure to login with callback: [%s], cause: %s", callback, e.getFaultString()));
    } catch (MalformedURLException e) {
      log.error(String.format("failure to login with callback: [%s], cause: %s", callback, e), e);
      throw new LoginException(String.format("failure to login with callback: [%s], cause: %s", callback, e));
    } catch (RemoteException e) {
      log.error(String.format("failure to login with callback: [%s], cause: %s", callback, e), e);
      throw new LoginException(String.format("failure to login with callback: [%s], cause: %s", callback, e));
    } catch (ServiceException e) {
      log.error(String.format("failure to login with callback: [%s], cause: %s", callback, e), e);
      throw new LoginException(String.format("failure to login with callback: [%s], cause: %s", callback, e));
    } finally {
      
    }
  }
}
