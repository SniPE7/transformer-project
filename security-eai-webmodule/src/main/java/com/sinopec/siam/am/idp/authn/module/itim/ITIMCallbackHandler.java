/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module.itim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.ibm.itim.ws.model.WSChallengeResponseInfo;

/**
 * 用于Handle TIM用户的登录, 支持用户名口令、用户名和密码问答方式的登录。<br/>
 * 
 * @author zhaodonglu
 *
 */
public class ITIMCallbackHandler implements CallbackHandler {

  /**
   * Locale for login user
   */
  private Locale locale = Locale.getDefault();
  
  /**
   * ITIM Username
   */
  private String principal = null;
  
  /**
   * ITIM Password
   */
  private String credential = null;
  
  /**
   * Responses for password and question
   */
  private List<WSChallengeResponseInfo> challendeReponseInfo = new ArrayList<WSChallengeResponseInfo>();

  public ITIMCallbackHandler() {
    super();
  }

  /**
   * @param principal  ITIM Username
   * @param credential ITIM Password
   */
  public ITIMCallbackHandler(String principal, String credential, Locale locale) {
    super();
    this.principal = principal;
    this.credential = credential;
    this.locale = locale;
  }

  public ITIMCallbackHandler(String principal, List<WSChallengeResponseInfo> challendeReponseInfo, Locale locale) {
    super();
    this.principal = principal;
    this.challendeReponseInfo = challendeReponseInfo;
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public String getCredential() {
    return credential;
  }

  public void setCredential(String credential) {
    this.credential = credential;
  }

  public List<WSChallengeResponseInfo> getChallendeReponseInfo() {
    return challendeReponseInfo;
  }

  public void setChallendeReponseInfo(List<WSChallengeResponseInfo> challendeReponseInfo) {
    this.challendeReponseInfo = challendeReponseInfo;
  }

  /* (non-Javadoc)
   * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
   */
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    if (callbacks == null || callbacks.length == 0) {
      return;
    }

    for (Callback cbs : callbacks) {
      if (cbs instanceof ITIMCallback) {
        ITIMCallback cb = (ITIMCallback) cbs;
        cb.setPrincipal(principal);
        cb.setCredential(credential);
        cb.setChallendeReponseInfo(challendeReponseInfo);
        cb.setLocale(locale);
      }
    }
  }

}
