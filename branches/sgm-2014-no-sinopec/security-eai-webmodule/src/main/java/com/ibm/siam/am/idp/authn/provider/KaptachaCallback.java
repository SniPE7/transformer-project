package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class KaptachaCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -8773266382479992523L;
  
  /**
   * ��̨���ɵ���֤��
   */
  private String kaptachaCode = null;
  
  /**
   * �û��������֤��
   */
  private String codeFromInput = null;

  public KaptachaCallback() {
    super();
  }

  public String getKaptachaCode() {
    return kaptachaCode;
  }

  public void setKaptachaCode(String kaptachaCode) {
    this.kaptachaCode = kaptachaCode;
  }

  public String getCodeFromInput() {
    return codeFromInput;
  }

  public void setCodeFromInput(String userInputCode) {
    this.codeFromInput = userInputCode;
  }

}
