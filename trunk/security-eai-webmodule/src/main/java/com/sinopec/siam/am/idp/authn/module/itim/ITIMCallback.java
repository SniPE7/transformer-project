package com.sinopec.siam.am.idp.authn.module.itim;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.Callback;

import com.ibm.itim.ws.model.WSChallengeResponseInfo;

/**
 * ITIM允许两种模式的登录, 基于用户名口令和基于密码问题答案（需要预先进行设置）.
 * 
 * @author zhaodonglu
 *
 */
public class ITIMCallback implements Callback {

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

  public ITIMCallback(String principal, Locale locale) {
    super();
    this.principal = principal;
    this.locale = locale;
  }

  public ITIMCallback() {
    super();
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

  @Override
  public String toString() {
    return String.format("ITIMCallback [locale=%s, principal=%s, credential=%s, challendeReponseInfo=%s]", locale, principal, "*******", "********");
  }

}