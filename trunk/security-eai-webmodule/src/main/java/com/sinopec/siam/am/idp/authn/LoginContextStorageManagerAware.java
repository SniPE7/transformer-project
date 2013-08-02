package com.sinopec.siam.am.idp.authn;

import org.opensaml.util.storage.StorageService;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;

public interface LoginContextStorageManagerAware {
  public void setStorageService(StorageService<String, LoginContextEntry> storageService);

}
