/**
 * 
 */
package com.sinopec.siam.am.idp.authn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.util.storage.StorageService;

import com.sinopec.siam.am.idp.management.IdPMonitor;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;

/**
 * @author zhaodonglu
 * 
 */
public class SimpleIdPMonitorEventListener implements LoginContextEventListener {

  private static Log log = LogFactory.getLog(SimpleIdPMonitorEventListener.class);

  private IdPMonitor idpMonitor = null;

  /**
   * Storage service used to store {@link LoginContext}s while authentication is
   * in progress.
   */
  private StorageService<String, LoginContextEntry> storageService;

  /**
   * 
   */
  public SimpleIdPMonitorEventListener() {
    super();
  }

  /**
   * @return the storageService
   */
  public StorageService<String, LoginContextEntry> getStorageService() {
    return storageService;
  }

  /**
   * @param storageService
   *          the storageService to set
   */
  public void setStorageService(StorageService<String, LoginContextEntry> storageService) {
    this.storageService = storageService;
  }

  /**
   * @return the idpMonitor
   */
  public IdPMonitor getIdpMonitor() {
    return idpMonitor;
  }

  /**
   * @param idpMonitor the idpMonitor to set
   */
  public void setIdpMonitor(IdPMonitor idpMonitor) {
    this.idpMonitor = idpMonitor;
  }

  public void fireEvent(LoginContextEvent event) {
    IdPMonitorEvent idpEvent = new IdPMonitorEvent(event.getCallbackHandler(), new IdPMonitorEventStatus(event.getStatus().isSuccess()));
    getIdpMonitor().recognizeAndRecord(storageService, idpEvent);
  }
}
