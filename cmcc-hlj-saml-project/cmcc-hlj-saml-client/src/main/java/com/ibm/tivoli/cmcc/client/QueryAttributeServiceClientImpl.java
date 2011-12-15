/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.request.QueryAttributeRequest;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;
import com.ibm.tivoli.cmcc.util.Helper;

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

  public QueryAttributeServiceClientImpl(Properties properties) {
    super(properties);
  }


  protected Object doBusiness(String id) throws ClientException {
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

  public QueryAttributeResponse submitAndParse(Connector connector, String samlId) throws ClientException {
    try {
      String xmlContent = this.submit(connector, samlId);
      return QueryAttributeResponse.parseResponse(xmlContent);
    } catch (IOException e) {
      throw new ClientException(String.format("fail to read xml response, connector: [%s], cause: [%s]", connector, e.getMessage()), e);
    } catch (SAXException e) {
      throw new ClientException(String.format("fail to parse xml response, connector: [%s], cause: [%s]", connector, e.getMessage()), e);
    }
  }


}
