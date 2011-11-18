/**
 * 
 */
package sample;

import java.util.Properties;

import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.client.QueryAttributeServiceClient;
import com.ibm.tivoli.cmcc.client.QueryAttributeServiceClientImpl;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;

/**
 * Please add all of lib/*.jar into your classpath.
 * 
 * @author Zhao Dong Lu
 *
 */
public class QueryAttributeClientSample {

  /**
   * 
   */
  public QueryAttributeClientSample() {
    super();
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    Properties properties = new Properties();
    properties.load(QueryAttributeClientSample.class.getResourceAsStream("/sample/saml-client-config.properties"));
    QueryAttributeServiceClient client = new QueryAttributeServiceClientImpl("10.110.21.53", 8080, properties);
    
    try {
      // Mode 1: Get SAML XML content
      String xmlResponse = client.submit("f57kyo7kjnthbjb5addnu8t0sooo2u6a");
      System.out.println(xmlResponse);
      
      // Mode 2: Get SAML XML content
      QueryAttributeResponse response = client.submitAndParse("f57kyo7kjnthbjb5addnu8t0sooo2u6a");
      String msisdn = response.getAttributeByIndex(0);
      System.out.println(msisdn);
    } catch (ClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
