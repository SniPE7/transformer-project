package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

public class FormOperationCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -8773266382479992523L;
  
  private String operation = null;

  public FormOperationCallback() {
    super();
  }

  public FormOperationCallback(String operation) {
    super();
    this.operation = operation;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

}
