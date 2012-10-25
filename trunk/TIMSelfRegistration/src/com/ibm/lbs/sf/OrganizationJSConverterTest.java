package com.ibm.lbs.sf;

import java.io.StringWriter;
import java.util.List;

import junit.framework.TestCase;

public class OrganizationJSConverterTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testConvert() throws Exception {
    OrganizationJSConverter converter = new OrganizationJSConverter();
    OrganizationService service = new SimpleOrgnizationService();
    List<Organization> topNodes = service.getAllOrganization();
    
    StringWriter content = new StringWriter();
    converter.convert(content, topNodes);
    System.out.println(content);
  }

}
