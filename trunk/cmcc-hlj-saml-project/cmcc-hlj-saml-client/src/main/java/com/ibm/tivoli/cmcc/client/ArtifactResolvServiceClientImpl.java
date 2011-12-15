/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.request.ArtifactResolvRequest;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ArtifactResolvServiceClientImpl extends BaseServiceClient implements ArtifactResolvServiceClient {
  
  /**
   * 
   */
  public ArtifactResolvServiceClientImpl() {
    super();
  }

  public ArtifactResolvServiceClientImpl(Properties properties) {
    super(properties);
  }

  public Object doBusiness(String artifact) throws ClientException {
    ArtifactResolvRequest request = new ArtifactResolvRequest();
    request.setSamlId(Helper.generatorID());
    request.setArtifact(artifact);
    request.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    request.setIssueInstant(Helper.formatDate4SAML(new Date()));
    return request;
  }

  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.artifactResolv.request", "classpath:/template/samlp.ArtifactResolv.request.template.xml");
  }

  public ArtifactResolvResponse submitAndParse(Connector connector, String artifact) throws ClientException {
    String xmlContent = null;
    try {
      xmlContent = this.submit(connector, artifact);
      return ArtifactResolvResponse.parseResponse(xmlContent);
    } catch (SAXException e) {
      throw new ClientException(String.format("fail to parse xml response: [%s], cause: [%s]", xmlContent, e.getMessage()), e);
    } catch (IOException e) {
      throw new ClientException(String.format("fail to read xml response: [%s], cause: [%s]", xmlContent, e.getMessage()), e);
    }
  }


}
