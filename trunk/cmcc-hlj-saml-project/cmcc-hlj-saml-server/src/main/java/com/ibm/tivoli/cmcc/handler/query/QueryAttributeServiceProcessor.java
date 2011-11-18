/**
 * 
 */
package com.ibm.tivoli.cmcc.handler.query;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.ldap.LDAPPersonDAO;
import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.ldap.PersonDTO;
import com.ibm.tivoli.cmcc.request.QueryAttributeRequest;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 *
 */
public class QueryAttributeServiceProcessor extends BaseProcessor implements Processor {
  
  private static Log log = LogFactory.getLog(QueryAttributeServiceProcessor.class);
  
  private PersonDTO personDTO = null;
  
  /**
   * 
   */
  public QueryAttributeServiceProcessor() {
    super();
  }
  
  public QueryAttributeServiceProcessor(Properties properties) {
    super(properties);
  }

  protected Digester setDigesterRules(Digester digester) {
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:AttributeQuery", "ID", "samlId");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:AttributeQuery", "IssueInstant", "issueInstant");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:AttributeQuery/saml:Issuer", "samlIssuer");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:AttributeQuery/saml:Subject/saml:NameID", "nameId");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:AttributeQuery/saml:Attribute", "Name", "attribute");
    return digester;

  }

  protected String getTemplateFile() {
    return this.getProperties().getProperty("messsage.template.attributeQuery.response", "classpath:/template/samlp.AttributeQuery.response.template.xml");
  }

  protected Object doBusiness(String in) throws IOException, SAXException {
    QueryAttributeRequest req = (QueryAttributeRequest)parseRequest(new QueryAttributeRequest(), in);
    QueryAttributeResponse resp = new QueryAttributeResponse(req);
    
    // Do business logic
    resp.setSamlId(Helper.generatorID());
    resp.setInResponseTo(req.getSamlId());
    resp.setIssueInstant(Helper.formatDate4SAML(new Date()));
    resp.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    resp.setNameId(req.getNameId());
    
    boolean found = false;
    
    try {
      PersonDAO dao = (LDAPPersonDAO) this.getApplicationContext().getBean("ldapDao");
      String filterPattern = this.getProperties().getProperty("ldap.filter.query.attribute.service", "(uniqueIdentifier=%UID)");
      String filter = StringUtils.replace(filterPattern, "%UID", req.getNameId());
      List<PersonDTO> persons = dao.searchPerson("", filter );
      if (persons != null && persons.size() > 0) {
         this.personDTO  = persons.get(0);
         this.personDTO.setProvince(this.getProperties().getProperty("message.saml.province.code"));
         log.debug("found ldap entity [uid=" + this.personDTO.getMsisdn() + "] by samlID: " + req.getNameId());
         found = true;
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    
    Calendar begin = Calendar.getInstance();
    begin.add(Calendar.MINUTE, -1 * Integer.parseInt(this.getProperties().getProperty("message.saml.conditionNotBefore")));
    Calendar end = Calendar.getInstance();
    end.add(Calendar.MINUTE, Integer.parseInt(this.getProperties().getProperty("message.saml.conditionNotOnOrAfter")));
    resp.setConditionNotBefore(Helper.formatDate4SAML(begin.getTime()));
    resp.setConditionNotOnOrAfter(Helper.formatDate4SAML(end.getTime()));
    
    if (found) {
       resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Success");
    } else {
      resp.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
    }
    return resp;
  }

  /**
   * Hook point for post process of template, you can override this method in subclass
   * @param result
   * @return
   */
  protected String postEvaluateTemplate(String result) {
    String s = result;
    if (this.personDTO != null) {
       try {
        s = this.evaluateTemplate(s, "person", BeanUtils.describe(this.personDTO));
      } catch (IllegalAccessException e) {
        log.error(e.getMessage(), e);
      } catch (InvocationTargetException e) {
        log.error(e.getMessage(), e);
      } catch (NoSuchMethodException e) {
        log.error(e.getMessage(), e);
      }
    }
    s = super.postEvaluateTemplate(s);
    return s;
  }
  

}
