/**
 * 
 */
package com.ibm.tivoli.cmcc.handler.passwordreset;


import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.dao.PersonDTO;
import com.ibm.tivoli.cmcc.handler.BaseProcessor;
import com.ibm.tivoli.cmcc.handler.Processor;
import com.ibm.tivoli.cmcc.request.PasswordResetRequest;
import com.ibm.tivoli.cmcc.response.PasswordResetResponse;
import com.ibm.tivoli.cmcc.spi.PersonDAO;

/**
 * @author Zhao Dong Lu
 *
 */
public class PasswordResetServiceProcessor extends BaseProcessor implements Processor {
  
  private static Log log = LogFactory.getLog(PasswordResetServiceProcessor.class);
  
  /**
   * 
   */
  public PasswordResetServiceProcessor() {
    super();
  }
  
  public PasswordResetServiceProcessor(Properties properties) {
    super(properties);
  }

  protected Digester setDigesterRules(Digester digester) {
    digester.addBeanPropertySetter("*/PasswordReset/UserName", "userName");
    digester.addBeanPropertySetter("*/PasswordReset/ServiceCode", "serviceCode");
    digester.addBeanPropertySetter("*/PasswordReset/NetworkPassword", "networkPassword");
    return digester;
  }

  protected String getTemplateFile() {
    return this.getProperties().getProperty("messsage.template.PasswordReset.response", "classpath:/template/samlp.PasswordResetResponse.template.xml");
  }

  protected Object doBusiness(String in) throws IOException, SAXException {
    PasswordResetRequest req = (PasswordResetRequest)parseRequest(new PasswordResetRequest(), in);
    PasswordResetResponse resp = new PasswordResetResponse();
    resp.setUserName(req.getUserName());
    
    // Callback external command
    String cmd = this.getProperties().getProperty("cmd.global.PasswordReset", "/usr/sbin/saml_PasswordReset");
    if (!StringUtils.isEmpty(cmd)) {
      try {
        String filter = "(uid=" + req.getUserName() + ")";
        PersonDAO dao = (PersonDAO) this.getApplicationContext().getBean("personDao");
        List<PersonDTO> persons = dao.searchPerson(filter );
        if (persons != null && persons.size() > 0) {
           PersonDTO personDTO  = persons.get(0);
           Runtime runtime = Runtime.getRuntime();
           String arg = personDTO.getMsisdn();
           String[] cmdarray = new String[]{cmd, arg};
           Process proc = runtime.exec(cmdarray);
           log.debug("execute external command for global PasswordReset, cmd: " + cmdarray);
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    } else {
      log.debug("undefined PasswordReset notify (external command)");
    }

    boolean success = true;
    String description = "成功";
    String resultCode = "1";
    try {
      String msisdn = req.getUserName();
      PersonDAO dao = (PersonDAO) this.getApplicationContext().getBean("personDao");
      success = dao.updatePassword(msisdn, req.getNetworkPassword());
      if (!success) {
        resultCode = "0";
        description = "失败";
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      success = false;
      resultCode = "0";
      description = e.getMessage();
    }
        
    // Do business logic
    resp.setResultCode(resultCode);
    resp.setDescription(description);
    return resp;
  }
}
