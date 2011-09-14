package com.ibm.tivoli.tools;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import junit.framework.TestCase;

public class TestHtml2MimeBodyPart extends TestCase {

  public TestHtml2MimeBodyPart(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testConvert() throws Exception {
    String html = "http://m.otas.cn/OTAS-DS-Web/common/DoLogin.do";
    URL url = new URL(html);
    Html2MimeBodyPart converter = new Html2MimeBodyPart(url);
    MimeBodyPart body = converter.getMimeBodyPart();
    String contentID = body.getContentID();
  }
  
  public void testSend() throws Exception {
    String mailHost = "mail.otas.cn";
    String from = "zhao@otas.cn";
    //String to = "machozhao@gmail.com";
    String to = "zhao@otas.cn";
    String subject = "Test Mime Body part";
    
    //String html = "http://m.otas.cn/OTAS-DS-Web/common/DoLogin.do";
    //URL url = new URL("http://www.meword.com/meword/Home.do");
    //Html2MimeBodyPart converter = new Html2MimeBodyPart(url);
    
    File file = new File("D:/Zhao/Code/MewordWorkspace/Meword/config/message/mail.template.html");
    Html2MimeBodyPart converter = new Html2MimeBodyPart(file);
    MimeBodyPart body = converter.getMimeBodyPart();

    boolean debug = false;
    try {

      Properties props = System.getProperties();
      // XXX - could use Session.getTransport() and Transport.connect()
      // XXX - assume we're using SMTP
      if (mailHost != null) {
        props.put("mail.smtp.host", mailHost);
      }

      // Get a Session object
      Session session = Session.getDefaultInstance(props, null);
      if (debug) {
        session.setDebug(true);
      }

      // construct the message
      MimeMessage msg = new MimeMessage(session);
      MimeMultipart multipart = new MimeMultipart();
      multipart.addBodyPart(body);
      msg.setContent(multipart);
      
      msg.setFrom(new InternetAddress(from));

      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
      msg.setSubject(subject);

      msg.setSentDate(new Date());

      // send the thing off
      Transport.send(msg);

    } catch (Exception e) {
      e.printStackTrace();
    }
  
  }
  

}
