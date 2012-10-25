package com.ibm.lbs.sf;

import java.util.List;

import junit.framework.TestCase;

public class SimpleOrganizationServiceTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSimpleOrganizationService() throws Exception {
    OrganizationService service = new SimpleOrgnizationService();
    List<Organization> result = service.getAllOrganization();
    assertEquals(2, result.size());
  }

  public void testOrganizationService() throws Exception {
    OrganizationService service = new OrganizationServiceImpl();
    List<Organization> result = service.getAllOrganization();
    assertEquals(2, result.size());
  }

  public void testCachableOrgnizationService() throws Exception {
    OrganizationService service = new CachableOrgnizationServiceImpl(new OrganizationServiceImpl());
    List<Organization> result = service.getAllOrganization();
    assertEquals(2, result.size());
  }
}
