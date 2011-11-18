package com.ibm.tivoli.cmcc.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class QueryAttributeResponseTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  private String getResourceAsString(String templatePath) throws UnsupportedEncodingException, IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(templatePath), "UTF-8"));
    StringWriter out = new StringWriter();
    String line = in.readLine();
    while (line != null) {
      out.write(line);
      line = in.readLine();
    }
    
    String templateContent = out.toString();
    return templateContent;
  }

  public void testParse1() throws Exception {
    String templatePath = "/template/samlp.AttributeQuery.response.template.xml";
    String templateContent = getResourceAsString(templatePath);
    QueryAttributeResponse resp = QueryAttributeResponse.parseResponse(templateContent);
    assertNotNull(resp);
    assertEquals(13, resp.getAttributes().size());
  }

  public void testParse2() throws Exception {
    String templatePath = "/com/ibm/tivoli/cmcc/response/samlp.AttributeQuery.response.1.xml";
    String templateContent = getResourceAsString(templatePath);
    QueryAttributeResponse resp = QueryAttributeResponse.parseResponse(templateContent);
    assertNotNull(resp);
    assertEquals(13, resp.getAttributes().size());
  }

  public void testParse3() throws Exception {
    String templatePath = "/com/ibm/tivoli/cmcc/response/samlp.AttributeQuery.response.2.xml";
    String templateContent = getResourceAsString(templatePath);
    QueryAttributeResponse resp = QueryAttributeResponse.parseResponse(templateContent);
    assertNotNull(resp);
    assertEquals(13, resp.getAttributes().size());
  }
}
