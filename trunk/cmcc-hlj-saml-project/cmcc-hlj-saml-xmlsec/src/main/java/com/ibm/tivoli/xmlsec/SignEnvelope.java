package com.ibm.tivoli.xmlsec;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.w3c.dom.Document;

public class SignEnvelope {

  private static Log log = LogFactory.getLog(SignEnvelope.class);

  private Properties cryptoFactoryProperties = null;
  private String signerKeyAlias = null;
  private String signerKeyPassword = null;

  /**
   * 
   */
  public SignEnvelope() {
    super();
  }

  /**
   * @param cryptoFactoryProperties
   * @param signerKeyAlias
   * @param signerKeyPassword
   */
  public SignEnvelope(Properties cryptoFactoryProperties, String signerKeyAlias, String signerKeyPassword) {
    super();
    this.cryptoFactoryProperties = cryptoFactoryProperties;
    this.signerKeyAlias = signerKeyAlias;
    this.signerKeyPassword = signerKeyPassword;
  }

  /**
   * @return the cryptoFactoryProperties
   */
  public Properties getCryptoFactoryProperties() {
    return cryptoFactoryProperties;
  }

  /**
   * @param cryptoFactoryProperties the cryptoFactoryProperties to set
   */
  public void setCryptoFactoryProperties(Properties cryptoFactoryProperties) {
    this.cryptoFactoryProperties = cryptoFactoryProperties;
  }

  /**
   * @return the signerKeyAlias
   */
  public String getSignerKeyAlias() {
    return signerKeyAlias;
  }

  /**
   * @param signerKeyAlias the signerKeyAlias to set
   */
  public void setSignerKeyAlias(String signerKeyAlias) {
    this.signerKeyAlias = signerKeyAlias;
  }

  /**
   * @return the signerKeyPassword
   */
  public String getSignerKeyPassword() {
    return signerKeyPassword;
  }

  /**
   * @param signerKeyPassword the signerKeyPassword to set
   */
  public void setSignerKeyPassword(String signerKeyPassword) {
    this.signerKeyPassword = signerKeyPassword;
  }

  private Document parseInputXML(InputStream input) {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // The DOM parser must be namespace aware
    factory.setNamespaceAware(true);

    Document parsedXML = null;
    try {
      log.debug("Parsing input XML file ...");

      DocumentBuilder builder = factory.newDocumentBuilder();
      parsedXML = builder.parse(input);
    } catch (Exception e) {
      log.error("Fail to parse xml content, cause: " + e.getMessage(), e);
    }

    return parsedXML;
  }

  private void writeOutput(Document toBeWritten, OutputStream output) {

    try {
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      DOMSource source = new DOMSource(toBeWritten);
      StreamResult result = new StreamResult(output);
      log.debug("Writing signed XML ");

      transformer.transform(source, result);
    } catch (Exception e) {
      log.error("Fail to signEnvelope, cause: " + e.getMessage(), e);
    }
  }

  public void signEnvelope(InputStream unsignedXMLInput, OutputStream output) {

    Document unsignedXML = this.parseInputXML(unsignedXMLInput);
    
    log.debug("Signing XML document ...");
    log.debug(" Creating Signature object ...");

    WSSecSignature signer = new WSSecSignature();
    signer.setUserInfo(signerKeyAlias, signerKeyPassword);
    signer.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);

    log.debug(" Creating WS-Security Header object ...");
    WSSecHeader header = new WSSecHeader();

    Document signedXML = null;

    try {
      header.insertSecurityHeader(unsignedXML);
      log.debug(" Creating Crypto object from " + this.cryptoFactoryProperties + " ...");
      Crypto crypto = CryptoFactory.getInstance(this.cryptoFactoryProperties);

      log.debug(" Writing Signature to WS-Security Header ...");

      signedXML = signer.build(unsignedXML, crypto, header);
      
      this.writeOutput(signedXML, output);
    } catch (Exception e) {
      log.error("Fail to signEnvelope, cause: " + e.getMessage(), e);
    }
  }

}
