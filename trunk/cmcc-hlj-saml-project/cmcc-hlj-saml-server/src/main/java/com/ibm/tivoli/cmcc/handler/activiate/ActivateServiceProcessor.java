/**
 * 
 */
package com.ibm.tivoli.cmcc.handler.activiate;


import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.ldap.LDAPPersonDAO;
import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.request.ActiviateRequest;
import com.ibm.tivoli.cmcc.response.ActiviateResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 *
 */
public class ActivateServiceProcessor extends BaseProcessor implements Processor {
  
  private static Log log = LogFactory.getLog(ActivateServiceProcessor.class);
  /**
   * 
   */
  public ActivateServiceProcessor() {
    super();
  }
  
  public ActivateServiceProcessor(Properties properties) {
    super(properties);
  }

  protected Digester setDigesterRules(Digester digester) {
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:ActivateRequest", "ID", "samlId");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:ActivateRequest", "IssueInstant", "issueInstant");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:ActivateRequest/saml:Issuer", "samlIssuer");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:ActivateRequest/saml:NameID", "nameId");
    return digester;

  }

  protected String getTemplateFile() {
    return this.getProperties().getProperty("messsage.template.activiate.response", "classpath:/template/samlp.ActivateResponse.template.xml");
  }


  protected Object doBusiness(String in) throws IOException, SAXException {
    ActiviateRequest req = (ActiviateRequest)parseRequest(new ActiviateRequest(), in);
    ActiviateResponse resp = new ActiviateResponse(req);
    
    boolean success = false;
    try {
      PersonDAO dao = (LDAPPersonDAO) this.getApplicationContext().getBean("ldapDao");
      String filterPattern = this.getProperties().getProperty("ldap.filter.query.attribute.service", "(uniqueIdentifier=%UID)");
      String filter = StringUtils.replace(filterPattern, "%UID", req.getNameId());
      success = dao.updateUniqueIdentifier(filter, req.getNameId());
    } catch (BeansException e) {
      log.error(e.getMessage(), e);
    }

    // Do business logic
    resp.setSamlId(Helper.generatorID());
    resp.setInResponseTo(req.getSamlId());
    resp.setNameId(req.getNameId());
    resp.setIssueInstant(Helper.formatDate4SAML(new Date()));
    resp.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    if (success) {
       resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Success");
    } else {
      resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requestor");
    }
    return resp;
  }
}
