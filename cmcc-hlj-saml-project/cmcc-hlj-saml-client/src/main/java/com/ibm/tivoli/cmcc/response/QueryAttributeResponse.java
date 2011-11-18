/**
 * 
 */
package com.ibm.tivoli.cmcc.response;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.request.QueryAttributeRequest;

public class QueryAttributeResponse {
  private String samlId = null;
  private String issueInstant = null;
  private String inResponseTo = null;
  private String samlIssuer = null;
  private String statusCode = "urn:oasis:names:tc:SAML:2.0:status:Success";
  private String nameId = null;
  
  private String conditionNotBefore = null;
  private String conditionNotOnOrAfter = null;
  private List attributes = new ArrayList();
  
  public QueryAttributeResponse() {
    super();
  }

  public QueryAttributeResponse(QueryAttributeRequest req) {
    this.inResponseTo = req.getSamlId();
  }
  
  public String getNameId() {
    return nameId;
  }

  public void setNameId(String nameId) {
    this.nameId = nameId;
  }

  public String getSamlId() {
    return samlId;
  }
  public void setSamlId(String samlId) {
    this.samlId = samlId;
  }
  public String getIssueInstant() {
    return issueInstant;
  }
  public void setIssueInstant(String issueInstant) {
    this.issueInstant = issueInstant;
  }
  public String getInResponseTo() {
    return inResponseTo;
  }
  public void setInResponseTo(String inResponseTo) {
    this.inResponseTo = inResponseTo;
  }
  public String getSamlIssuer() {
    return samlIssuer;
  }
  public void setSamlIssuer(String samlIssuer) {
    this.samlIssuer = samlIssuer;
  }
  public String getStatusCode() {
    return statusCode;
  }
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getConditionNotBefore() {
    return conditionNotBefore;
  }

  public void setConditionNotBefore(String conditionNotBefore) {
    this.conditionNotBefore = conditionNotBefore;
  }

  public String getConditionNotOnOrAfter() {
    return conditionNotOnOrAfter;
  }

  public void setConditionNotOnOrAfter(String conditionNotOnOrAfter) {
    this.conditionNotOnOrAfter = conditionNotOnOrAfter;
  }
  
  public void setAttribute(String value) {
    this.attributes .add(value);
  }
  
  /**
   * String arrays
   * @return
   */
  public List getAttributes() {
    return this.attributes;
  }
  
  /**
   * Begin with 0
   * @return
   */
  public String getAttributeByIndex(int index) {
    if (this.attributes.size() > index) {
       return (String)this.attributes.get(index);
    } else {
      return null;
    }
  }
  
  protected static Digester setDigesterRules(Digester digester) {
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response", "ID", "samlId");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response", "InResponseTo", "inResponseTo");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response", "IssueInstant", "issueInstant");
    
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Issuer", "samlIssuer");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/samlp:Status/samlp:StatusCode", "Value", "statusCode");
    
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Assertion/saml:Subject/saml:NameID", "nameId");
    
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Assertion/saml:Conditions/", "NotBefore", "conditionNotBefore");
    digester.addSetProperties("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Assertion/saml:Conditions/", "NotOnOrAfter", "conditionNotOnOrAfter");
    
    //digester.addCallMethod("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Assertion/saml:AttributeStatement/saml:Attribute/saml:AttributeValue", "putAttribute");
    digester.addBeanPropertySetter("*/SOAP-ENV:Envelope/SOAP-ENV:Body/samlp:Response/saml:Assertion/saml:AttributeStatement/saml:Attribute/saml:AttributeValue", "attribute");
    
    return digester;

  }

  protected static Digester getDigester() {
    Digester digester = new Digester();
    digester.setNamespaceAware(false);
    digester.setValidating(false);

    setDigesterRules(digester);
    return digester;

  }

  public static QueryAttributeResponse parseResponse(String in) throws IOException, SAXException {
    QueryAttributeResponse resp = new QueryAttributeResponse();
    Digester digester = getDigester();
    digester.push(resp);
    try {
      if (!in.trim().toLowerCase().startsWith("<?xml")) {
        in = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + in;
      }
      digester.parse(new StringReader(in));
      return resp;
    } catch (IOException e) {
      throw e;
    } catch (SAXException e) {
      throw e;
    }
  }
  
}