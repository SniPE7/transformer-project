/**
 * 
 */
package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * ��ȡ�Ѿ������֤��Principal��Ϣ, ����֧��Step-upģʽ����֤
 * @author zhaodonglu
 *
 */
public class LastAuthenticatedPrincipalCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 719078341041601899L;
  
  private String principalName = null;

  /**
   * 
   */
  public LastAuthenticatedPrincipalCallback() {
    super();
  }

  public String getPrincipalName() {
    return principalName;
  }

  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }

}
