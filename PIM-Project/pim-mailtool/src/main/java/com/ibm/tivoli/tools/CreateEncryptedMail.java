package com.ibm.tivoli.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;

/**
 * a simple example that creates a single encrypted mail message.
 * <p>
 * The key store can be created using the class in
 * org.bouncycastle.jce.examples.PKCS12Example - the program expects only one
 * key to be present in the key file.
 * <p>
 * Note: while this means that both the private key is available to the program,
 * the private key is retrieved from the keystore only for the purposes of
 * locating the corresponding public key, in normal circumstances you would only
 * be doing this with a certificate available.
 */
public class CreateEncryptedMail {
  public static void main(String args[]) throws Exception {
    /*
    if (args.length != 2) {
      System.err.println("usage: CreateEncryptedMail pkcs12Keystore password");
      System.exit(0);
    }
    */
    //
    // Open the key store
    //
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    KeyStore ks = KeyStore.getInstance("PKCS12", "BC");

    ks.load(new FileInputStream("C:/temp/certs/mystore.p12"), "abc123".toCharArray());

    Enumeration e = ks.aliases();
    String keyAlias = null;

    while (e.hasMoreElements()) {
      String alias = (String) e.nextElement();

      if (ks.isKeyEntry(alias)) {
        keyAlias = alias;
      }
    }

    if (keyAlias == null) {
      System.err.println("can't find a private key!");
      System.exit(0);
    }

    Certificate[] chain = ks.getCertificateChain(keyAlias);

    //
    // create the generator for creating an smime/encrypted message
    //
    SMIMEEnvelopedGenerator gen = new SMIMEEnvelopedGenerator();

    gen.addKeyTransRecipient((X509Certificate) chain[0]);

    //
    // create a subject key id - this has to be done the same way as
    // it is done in the certificate associated with the private key
    // version 3 only.
    //
    /*
     * MessageDigest dig = MessageDigest.getInstance("SHA1", "BC");
     * 
     * dig.update(cert.getPublicKey().getEncoded());
     * 
     * gen.addKeyTransRecipient(cert.getPublicKey(), dig.digest());
     */

    //
    // create the base for our message
    //
    MimeBodyPart msg = new MimeBodyPart();

    msg.setText("Hello world!");

    MimeBodyPart mp = gen.generate(msg, SMIMEEnvelopedGenerator.RC2_CBC, "BC");
    //
    // Get a Session object and create the mail message
    //
    Properties props = System.getProperties();
    props.put("mail.smtp.host", "192.168.2.131");
    Session session = Session.getDefaultInstance(props, null);

    Address fromUser = new InternetAddress("\"Test ABC\"<abc@test.com>");
    Address toUser = new InternetAddress("abc@test.com");

    MimeMessage body = new MimeMessage(session);
    body.setFrom(fromUser);
    body.setRecipient(Message.RecipientType.TO, toUser);
    body.setSubject("example encrypted message");
    body.setContent(mp.getContent(), mp.getContentType());
    body.saveChanges();

    body.writeTo(new FileOutputStream("c:/temp/encrypted.message"));
    
    // XXX - could use Session.getTransport() and Transport.connect()
    // XXX - assume we're using SMTP
    session.setDebug(true);
    // send the thing off
    Transport tr = session.getTransport("smtp");
    tr.connect(props.getProperty("mail.smtp.host"), "abc", "passw0rd");
    tr.send(body);
    System.out.println("Sending Email: Success");
    
  }
}
