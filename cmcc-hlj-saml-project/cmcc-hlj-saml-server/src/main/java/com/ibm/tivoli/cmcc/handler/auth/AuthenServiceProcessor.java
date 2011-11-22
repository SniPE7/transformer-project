/**
 * 
 */
package com.ibm.tivoli.cmcc.handler.auth;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.ldap.LDAPPersonDAO;
import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.ldap.PersonDTO;
import com.ibm.tivoli.cmcc.request.LogoutRequest;
import com.ibm.tivoli.cmcc.response.LogoutResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 *
 */
public class AuthenServiceProcessor extends BaseProcessor implements Processor {
  
  private static Log log = LogFactory.getLog(AuthenServiceProcessor.class);
  
  /**
   * 
   */
  public AuthenServiceProcessor() {
    super();
  }
  
  public AuthenServiceProcessor(Properties properties) {
    super(properties);
  }

  protected Digester setDigesterRules(Digester digester) {
    digester.addSetProperties("*/samlp:AuthnRequest", "ID", "samlId");
    digester.addSetProperties("*/samlp:AuthnRequest", "IssueInstant", "issueInstant");
    digester.addBeanPropertySetter("*/samlp:AuthnRequest/saml:Issuer", "samlIssuer");
    digester.addSetProperties("*/samlp:AuthnRequest/samlp:NameIDPolicy", "AllowCreate", "allowCreate");
    return digester;

  }

  protected String getTemplateFile() {
    return this.getProperties().getProperty("messsage.template.authen.response", "classpath:/template/samlp.AuthenResponse.template.xml");
  }


  protected Object doBusiness(String in) throws IOException, SAXException {
    LogoutRequest req = (LogoutRequest)parseRequest(new LogoutRequest(), in);
    LogoutResponse resp = new LogoutResponse(req);
    
    PersonDAO dao = (LDAPPersonDAO) this.getApplicationContext().getBean("ldapDao");
    // Callback external command
    String cmd = this.getProperties().getProperty("cmd.global.logout", "/usr/sbin/saml_logout");
    if (!StringUtils.isEmpty(cmd)) {
      try {
        String filterPattern = this.getProperties().getProperty("ldap.filter.query.attribute.service", "(uniqueIdentifier=%UID)");
        String filter = StringUtils.replace(filterPattern, "%UID", req.getNameId());
        List<PersonDTO> persons = dao.searchPerson("", filter );
        if (persons != null && persons.size() > 0) {
           PersonDTO personDTO  = persons.get(0);
           Runtime runtime = Runtime.getRuntime();
           String arg = personDTO.getMsisdn();
           String[] cmdarray = new String[]{cmd, arg};
           Process proc = runtime.exec(cmdarray);
           log.debug("execute external command for global logout, cmd: " + cmdarray);
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    } else {
      log.debug("undefined logout notify (external command)");
    }

    try {
      String filterPattern = this.getProperties().getProperty("ldap.filter.query.attribute.service", "(uniqueIdentifier=%UID)");
      String filter = StringUtils.replace(filterPattern, "%UID", req.getNameId());
      dao.deleteUniqueIdentifier("", filter, req.getNameId());
      log.debug("");
    } catch (BeansException e) {
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
