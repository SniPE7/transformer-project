/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.request.LogoutRequest;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LogoutServiceClientImpl extends BaseServiceClient implements LogoutServiceClient {
  
  /**
   * 
   */
  public LogoutServiceClientImpl() {
    super();
  }

  public LogoutServiceClientImpl(Properties properties) {
    super(properties);
  }

  protected Object doBusiness(String id) throws ClientException {
    LogoutRequest request = new LogoutRequest();
    request.setSamlId(Helper.generatorID());
    request.setNameId(id);
    request.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    request.setIssueInstant(Helper.formatDate4SAML(new Date()));
    return request;
  }

  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.logout.request", "classpath:/template/samlp.LogoutRequest.template.xml");
  }
}
