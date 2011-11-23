/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.request.ArtifactResolvRequest;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

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

  public ArtifactResolvServiceClientImpl(String serverName, int serverPort, Properties properties) {
    super(serverName, serverPort, properties);
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

  public ArtifactResolvResponse submitAndParse(String artifact) throws ClientException {
    try {
      String xmlContent = this.submit(artifact);
      return ArtifactResolvResponse.parseResponse(xmlContent);
    } catch (IOException e) {
      throw new ClientException("fail to send request, cause: " + e.getMessage(), e);
    } catch (SAXException e) {
      throw new ClientException("fail to parse xml response, cause: " + e.getMessage(), e);
    }
  }


}
