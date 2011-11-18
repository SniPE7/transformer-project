/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.request.QueryAttributeRequest;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class QueryAttributeServiceClientImpl extends BaseServiceClient implements QueryAttributeServiceClient {
  
  /**
   * 
   */
  public QueryAttributeServiceClientImpl() {
    super();
  }

  public QueryAttributeServiceClientImpl(String serverName, int serverPort, Properties properties) {
    super(serverName, serverPort, properties);
  }

  public Object doBusiness(String id) throws ClientException {
    QueryAttributeRequest request = new QueryAttributeRequest();
    request.setSamlId(Helper.generatorID());
    request.setNameId(id);
    request.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    request.setIssueInstant(Helper.formatDate4SAML(new Date()));
    return request;
  }

  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.attributeQuery.request", "classpath:/template/samlp.AttributeQuery.request.template.xml");
  }

  public QueryAttributeResponse submitAndParse(String samlId) throws ClientException {
    try {
      String xmlContent = this.submit(samlId);
      return QueryAttributeResponse.parseResponse(xmlContent);
    } catch (IOException e) {
      throw new ClientException("fail to send request, cause: " + e.getMessage(), e);
    } catch (SAXException e) {
      throw new ClientException("fail to parse xml response, cause: " + e.getMessage(), e);
    }
  }


}
