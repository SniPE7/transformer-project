/**
 * 
 */
package com.ibm.tivoli.cmcc.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor;
import com.ibm.tivoli.cmcc.handler.logout.LogoutServiceProcessor;
import com.ibm.tivoli.cmcc.handler.query.QueryAttributeServiceProcessor;
import com.ibm.tivoli.cmcc.request.ActiviateRequest;
import com.ibm.tivoli.cmcc.request.LogoutRequest;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 *
 */
public class ActivateServiceProcessorTest extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
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

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor#parseRequest(java.lang.String)}.
   */
  public void testActivateParseRequest() throws Exception {
    String templatePath = "/template/samlp.ActivateRequest.template.xml";
    String templateContent = getResourceAsString(templatePath);
    
    BaseProcessor processor = new ActivateServiceProcessor();
    ActiviateRequest request = (ActiviateRequest)processor.parseRequest(new ActiviateRequest(), templateContent);
    
    assertNotNull(request);
    assertEquals("id_o1", request.getSamlId());
    assertEquals("2007-12-05T09:27:05Z", request.getIssueInstant());
    assertEquals("UID", request.getNameId());
    assertEquals("https://ac.chinamobile.com/SSO", request.getSamlIssuer());
    
    
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor#parseRequest(java.lang.String)}.
   */
  public void testActivateProcess() throws Exception {
    String templatePath = "/template/samlp.ActivateRequest.template.xml";
    String templateContent = getResourceAsString(templatePath);
    
    BaseProcessor processor = new ActivateServiceProcessor();
    
    String resp = processor.process(templateContent);
    System.err.println(resp);
    Diff diff = new Diff("<SOAP-ENV:Envelope\n" + 
        "  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
        "  <SOAP-ENV:Body>\n" + 
        "    <samlp:ActivateResponse\n" + 
        "        xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n" + 
        "        xmlns=\"urn:oasis:names:tc:SAML:2.0:assertion\"\n" + 
        "        ID=\"id_o2\"\n" + 
        "        InResponseTo=\"id_o1\"\n" + 
        "        IssueInstant=\"2010-07-09T09:04:52+0800\"\n" + 
        "        Version=\"2.0\">\n" + 
        "        <saml:Issuer></saml:Issuer>\n" + 
        "        <samlp:Status>\n" + 
        "            <samlp:StatusCode\n" + 
        "                Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\"/>\n" + 
        "        </samlp:Status>\n" + 
        "    </samlp:ActivateResponse>\n" + 
        "  </SOAP-ENV:Body>\n" + 
        "</SOAP-ENV:Envelope>\n", resp);
    
    //diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

    assertTrue("XML Similar " + diff.toString(), diff.similar());

  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor#parseRequest(java.lang.String)}.
   */
  public void testLogoutParseRequest() throws Exception {
    String templatePath = "/template/samlp.LogoutRequest.templdate.xml";
    String templateContent = getResourceAsString(templatePath);
    
    BaseProcessor processor = new LogoutServiceProcessor();
    LogoutRequest request = (LogoutRequest)processor.parseRequest(new LogoutRequest(), templateContent);
    
    assertNotNull(request);
    assertEquals("id_o1", request.getSamlId());
    assertEquals("2007-12-05T09:27:05Z", request.getIssueInstant());
    assertEquals("UID", request.getNameId());
    assertEquals("https://ac.chinamobile.com/SSO", request.getSamlIssuer());
    
    
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor#parseRequest(java.lang.String)}.
   */
  public void testLogoutProcess() throws Exception {
    String templatePath = "/template/samlp.LogoutRequest.templdate.xml";
    String templateContent = getResourceAsString(templatePath);
    
    Processor processor = new LogoutServiceProcessor();
    
    String resp = processor.process(templateContent);
    System.err.println(resp);
    assertEquals("", resp);
  }
  
  public void testRetrieveServiceProcessor() throws Exception {
    ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "/com/ibm/tivoli/cmcc/server/spring/mainBean.xml" });
    Properties props = new Properties();
    props.load(this.getClass().getResourceAsStream("/com/ibm/tivoli/cmcc/server/spring/saml-server-config.properties"));
    
    QueryAttributeServiceProcessor processor = new QueryAttributeServiceProcessor();
    processor.setApplicationContext(factory);
    processor.setProperties(props);
    
    String templatePath = "/template/samlp.AttributeQuery.request.template.xml";
    String templateContent = getResourceAsString(templatePath);
    String out = processor.process(templateContent);
    System.err.println(out);
  }
  
  public void testReplace() throws Exception {
    String a = "aaa${abc}dhjahd${a.n0.cde}js";
    a = a.replaceAll("\\$\\{[a-zA-Z0-9.]*\\}", "");
    assertEquals("aaadhjahdjs", a);
  }
  
  public void testGenerateID() throws Exception {
    assertEquals(32, Helper.generatorID().length());
    assertEquals(32, Helper.generatorID().length());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
    assertNotSame(Helper.generatorID(), Helper.generatorID());
  }

}
