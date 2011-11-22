package com.ibm.tivoli.xmlsec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;

public class SignEnvelopeTest extends TestCase {

  private Properties cryptoFactoryProperties;

  protected void setUp() throws Exception {
    super.setUp();
    cryptoFactoryProperties = new Properties();
    cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
    // cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.file",
    // "C:/Users/IBM_ADMIN/workspace/XMLSecTest/sample.jks");
    // cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type",
    // "jks");
    // cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password",
    // "password");
    cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.file", "C:/temp/ca/mystore.jks");
    cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
    cryptoFactoryProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", "passw0rd");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    ByteArrayOutputStream xml = new ByteArrayOutputStream();
    SignEnvelope sign = new SignEnvelope(cryptoFactoryProperties, "my", "passw0rd");
    // InputStream src =
    // this.getClass().getResourceAsStream("/com/ibm/tivoli/xmlsec/unsigned.xml");
    InputStream src = new ByteArrayInputStream(("<?xml version=\"1.0\"?>\n"
        + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + "  <soapenv:Header/>\n" + "  <soapenv:Body>\n" + "    <books>\n"
        + "      <book>\n" + "        <title>War and Peace</title>\n" + "        <author>Leo Tolstoy</author>\n" + "      </book>\n" + "      <book>\n"
        + "        <title>The Catcher in the Rye</title>\n" + "        <author>J. D. Salinger</author>\n" + "      </book>\n" + "    </books>\n"
        + "  </soapenv:Body>\n" + "</soapenv:Envelope>\n").getBytes());
    sign.signEnvelope(src, xml);
    System.out.println(xml.toString());
  }

  public void testCase2() throws Exception {
    /*
    InputStream src = new ByteArrayInputStream(("<?xml version=\"1.0\"?>\n"
        + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + "  <soapenv:Header/>\n" + "  <soapenv:Body>\n" + "    <books>\n"
        + "      <book>\n" + "        <title>War and Peace</title>\n" + "        <author>Leo Tolstoy</author>\n" + "      </book>\n" + "      <book>\n"
        + "        <title>The Catcher in the Rye</title>\n" + "        <author>J. D. Salinger</author>\n" + "      </book>\n" + "    </books>\n"
        + "  </soapenv:Body>\n" + "</soapenv:Envelope>\n").getBytes());
    */
    
    InputStream src = this.getClass().getResourceAsStream("/test/unsigned.xml");
    //InputStream src = this.getClass().getResourceAsStream("/test/unsigned-fixed.xml");
    
    // Create a DOM XMLSignatureFactory that will be used to
    // generate the enveloped signature.
    XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

    // Create a Reference to the enveloped document (in this case,
    // you are signing the whole document, so a URI of "" signifies
    // that, and also specify the SHA1 digest algorithm and
    // the ENVELOPED Transform.
    Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
        Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);

    // Create the SignedInfo.
    SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
        fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

    // Load the KeyStore and get the signing key and certificate.
    KeyStore ks = KeyStore.getInstance("JKS");
    ks.load(new FileInputStream("C:/temp/ca/mystore.jks"), "passw0rd".toCharArray());
    KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("my", new KeyStore.PasswordProtection("passw0rd".toCharArray()));
    X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

    // Create the KeyInfo containing the X509Data.
    KeyInfoFactory kif = fac.getKeyInfoFactory();
    List x509Content = new ArrayList();
    x509Content.add(cert.getSubjectX500Principal().getName());
    x509Content.add(cert);
    X509Data xd = kif.newX509Data(x509Content);
    KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

    // Instantiate the document to be signed.
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(false);
    dbf.setValidating(false);
    Document doc = dbf.newDocumentBuilder().parse(src);

    // Create a DOMSignContext and specify the RSA PrivateKey and
    // location of the resulting XMLSignature's parent element.
    DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());

    // Create the XMLSignature, but don't sign it yet.
    XMLSignature signature = fac.newXMLSignature(si, ki);

    // Marshal, generate, and sign the enveloped signature.
    signature.sign(dsc);

    // Output the resulting document.
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer trans = tf.newTransformer();
    trans.transform(new DOMSource(doc), new StreamResult(os));
    //trans.transform(new DOMSource(), new StreamResult(os));
    System.out.println(os.toString());
  }

}
