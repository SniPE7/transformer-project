/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.request.ActiviateRequest;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ActiviateServiceClientImpl extends BaseServiceClient implements ActiviateServiceClient {
  
  /**
   * 
   */
  public ActiviateServiceClientImpl() {
    super();
  }

  public ActiviateServiceClientImpl(ConnectorManager networkConnectorManager, Properties properties) {
    super(networkConnectorManager, properties);
  }

  public Object doBusiness(String id) throws ClientException {
    ActiviateRequest request = new ActiviateRequest();
    request.setSamlId(Helper.generatorID());
    request.setNameId(id);
    request.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    request.setIssueInstant(Helper.formatDate4SAML(new Date()));
    return request;
  }

  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.activiate.request", "classpath:/template/samlp.ActivateRequest.template.xml");
  }
}
