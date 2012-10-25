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

  public void testGetAllOrganization() throws Exception {
    OrgnizationService service = new SimpleOrgnizationService();
    List<Organization> result = service.getAllOrganization();
    assertEquals(2, result.size());
  }

}
