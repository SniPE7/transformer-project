/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public class ChainLoginModuleEventListener implements LoginModuleEventListener {

  private List<LoginModuleEventListener> listeners = new ArrayList<LoginModuleEventListener>();
  /**
   * 
   */
  public ChainLoginModuleEventListener() {
    super();
  }

  public ChainLoginModuleEventListener(List<LoginModuleEventListener> listeners) {
    super();
    this.listeners = listeners;
  }

  public List<LoginModuleEventListener> getListeners() {
    return listeners;
  }

  public void setListeners(List<LoginModuleEventListener> listeners) {
    this.listeners = listeners;
  }

  public void fireEvent(LoginModuleEvent event) {
    for (LoginModuleEventListener listener: this.listeners) {
       listener.fireEvent(event);
    }
  }

}
