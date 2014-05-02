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
public class ChainLoginContextEventListener implements LoginContextEventListener {

  private List<LoginContextEventListener> listeners = new ArrayList<LoginContextEventListener>();
  /**
   * 
   */
  public ChainLoginContextEventListener() {
    super();
  }

  public ChainLoginContextEventListener(List<LoginContextEventListener> listeners) {
    super();
    this.listeners = listeners;
  }

  public List<LoginContextEventListener> getListeners() {
    return listeners;
  }

  public void setListeners(List<LoginContextEventListener> listeners) {
    this.listeners = listeners;
  }

  public void fireEvent(LoginContextEvent event) {
    for (LoginContextEventListener listener: this.listeners) {
       listener.fireEvent(event);
    }
  }

}
