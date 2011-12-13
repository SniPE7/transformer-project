/**
 * 
 */
package com.ibm.tivoli.cmcc.handler.logout;


import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.request.LogoutRequest;
import com.ibm.tivoli.cmcc.response.LogoutResponse;
import com.ibm.tivoli.cmcc.session.SessionManager;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author Zhao Dong Lu
 *
 */
public class LogoutServiceProcessor extends BaseProcessor implements Processor {
  
  private static Log log = LogFactory.getLog(LogoutServiceProcessor.class);
  
  /**
   * 
   */
  public LogoutServiceProcessor() {
    super();
  }
  
  public LogoutServiceProcessor(Properties properties) {
    super(properties);
  }

  protected Digester setDigesterRules(Digester digester) {
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:LogoutRequest", "ID", "samlId");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:LogoutRequest", "IssueInstant", "issueInstant");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:LogoutRequest/saml:Issuer", "samlIssuer");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:LogoutRequest/saml:NameID", "nameId");
    return digester;

  }

  protected String getTemplateFile() {
    return this.getProperties().getProperty("messsage.template.logout.response", "classpath:/template/samlp.LogoutResponse.template.xml");
  }


  protected Object doBusiness(String in) throws IOException, SAXException {
    LogoutRequest req = (LogoutRequest)parseRequest(new LogoutRequest(), in);
    LogoutResponse resp = new LogoutResponse(req);
    
    try {
      Helper.validateArtifactID(req.getNameId());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      resp.setSamlId(Helper.generatorID());
      resp.setInResponseTo(req.getSamlId());
      resp.setIssueInstant(Helper.formatDate4SAML(new Date()));
      resp.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
      resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requestor");
      return resp;
    }
    
    // Do business logic
    try {
      SessionManager sessionManager = (SessionManager) this.getApplicationContext().getBean("sessionManager");
      sessionManager.destroy(req.getNameId(), false);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
        
    // Do business logic
    resp.setSamlId(Helper.generatorID());
    resp.setInResponseTo(req.getSamlId());
    resp.setIssueInstant(Helper.formatDate4SAML(new Date()));
    resp.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Success");
    return resp;
  }
}
