/**
 * 
 */
package com.ibm.lbs.sf;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class CachableOrgnizationServiceImpl implements OrganizationService {

  private int cacheExpireInSeconds = 1800;
  private OrganizationService service = null;

  private List<Organization> cachedTopNodes = null;

  private Date lastUpdateTime = new Date(0L);

  /**
   * 
   */
  public CachableOrgnizationServiceImpl(OrganizationService service) {
    super();
    this.service = service;
  }

  public CachableOrgnizationServiceImpl(OrganizationService service, int cacheExpireInSeconds) {
    super();
    this.service = service;
    this.cacheExpireInSeconds = cacheExpireInSeconds;
  }

  @Override
  public synchronized List<Organization> getAllOrganization() throws Exception {
    if (cachedTopNodes == null || System.currentTimeMillis() - lastUpdateTime.getTime() > cacheExpireInSeconds * 1000) {
      List<Organization> result = this.service.getAllOrganization();
      cachedTopNodes = result;
      lastUpdateTime = new Date();
    }
    return cachedTopNodes;
  }
}
