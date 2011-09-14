package com.ibm.tivoli.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sendmail {

  private String protocol = "smtp";

  private String mailHost = "127.0.0.1";

  private String xMailer = "Java Mailer";

  private String user = null;

  private String password = null;
  
  private String charset = "UTF-8";

  /**
   * 
   */
  public Sendmail() {
    super();
  }

  /**
   * @param mailHost
   * @param user
   * @param password
   */
  public Sendmail(String mailHost, String user, String password) {
    super();
    this.mailHost = mailHost;
    this.user = user;
    this.password = password;
  }

  /**
   * @param mailHost
   */
  public Sendmail(String mailHost) {
    super();
    this.mailHost = mailHost;
  }

  public void sendByTemplateFile(String from, String to, String subject, String templateFilename, String charsetTemplateFile, Map properties) throws IOException {
    this.sendByTemplateFile(from, to, subject, templateFilename, charsetTemplateFile, properties, (String[])null);
  }
  
  public void sendByTemplateFile(String from, String to, String subject, String templateFilename, String charsetTemplateFile, Map properties, String attachmentFile) throws IOException {
    String[] attachmentFiles = null;
    if (attachmentFile != null) {
      attachmentFiles = new String[]{attachmentFile};
    }
    this.sendByTemplateFile(from, to, subject, templateFilename, charsetTemplateFile, properties, attachmentFiles);
  }
  
  /**
   * @param from
   * @param to
   * @param subject
   * @param templateFilename
   * @param charsetTemplateFile
   * @param properties
   * @throws IOException
   */
  public void sendByTemplateFile(String from, String to, String subject, String templateFilename, String charsetTemplateFile, Map properties, String[] attachmentFiles) throws IOException {
    String mailTemplate = this.loadTemplate(templateFilename, charsetTemplateFile);
    String content = this.merge(mailTemplate, properties);
    this.send(from, to, subject, content, attachmentFiles);
  }

  /**
   * @param from
   * @param to
   * @param subject
   * @param content
   */
  public void send(String from, String to, String subject, String content, String[] attachmentFiles) {
    send(from, to, null, null, subject, content, attachmentFiles);
  }

  public void send(String from, String to, String cc, String bcc, String subject, String content, String[] attachmentFiles) {

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
      if (from != null) {
        msg.setFrom(new InternetAddress(from));
      } else {
        msg.setFrom(new InternetAddress(from));
      }

      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
      if (cc != null) {
        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
      }
      if (bcc != null) {
        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
      }

      msg.setSubject(subject, this.charset);

      // msg.setDataHandler(new javax.activation.DataHandler(new
      // ByteArrayDataSource(content, "text/html;charset=UTF-8", "UTF-8")));

      MimeMultipart multipart = new MimeMultipart();
      Html2MimeBodyPart converter = new Html2MimeBodyPart(content, this.charset);
      MimeBodyPart body = converter.getMimeBodyPart();
      multipart.addBodyPart(body);
      msg.setContent(multipart);
      for (int i = 0; attachmentFiles != null && i < attachmentFiles.length; i++) {
        String attachmentFile = attachmentFiles[i];
        MimeBodyPart attachment = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentFile);
        attachment.setDataHandler(new DataHandler(source));
        FileTypeMap map = MimetypesFileTypeMap.getDefaultFileTypeMap();
        attachment.setHeader("Content-Type", map.getContentType(attachmentFile));
        attachment.setFileName((new File(attachmentFile)).getName());
        multipart.addBodyPart(attachment);
      }

      msg.setHeader("X-Mailer", xMailer);
      msg.setSentDate(new Date());

      System.out.println("Sending Email: " + msg);
      System.out.println("Sending Email, To: " + to + ", Subject: " + subject);
      // send the thing off
      Transport tr = session.getTransport(this.protocol);
      tr.connect(this.mailHost, this.user, this.password);
      tr.send(msg);
      System.out.println("Sending Email: Success");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Load template content from file.
   * 
   * @param filename
   *          name and path of template file
   * @param charset
   *          charset of template file.
   * @return
   * @throws IOException
   */
  public String loadTemplate(String filename, String charset) throws IOException {
    FileInputStream in = new FileInputStream(filename);
    return loadTemplateFromStream(in, charset);
  }

  /**
   * @param in
   * @param charset
   * @return
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  public String loadTemplateFromStream(InputStream in, String charset) throws UnsupportedEncodingException, IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
    String templateContent = "";
    String line = null;
    while ((line = reader.readLine()) != null) {
      templateContent += line + "\n";
    }
    return templateContent;
  }

  /**
   * 合并模板：将模板中的${key}替换为properties中提供的值.
   * 
   * @param templateContent
   * @param properties
   * @return
   */
  public String merge(String templateContent, Map properties) {
    Set keys = properties.keySet();
    Iterator i = keys.iterator();
    while (i.hasNext()) {
      String name = (String) i.next();
      templateContent = templateContent.replaceAll("\\$\\{" + name + "\\}", properties.get(name).toString());
    }
    return templateContent;
  }

  public void setMailHost(String mailHost) {
    this.mailHost = mailHost;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public void setCharset(String charset) {
    this.charset = charset;
  }

  public static void main(String[] argv) throws Exception {
    com.ibm.tivoli.tools.Sendmail sendmail = new com.ibm.tivoli.tools.Sendmail("192.168.2.131");

    Map properties = new HashMap();
    properties.put("person.cn", "白明辉111,Bai Minghui");
    properties.put("person.uid", "sdhihu222");

    // Case#1
    String from = "abc@test.com";
    String to = "abc@test.com";
    sendmail.sendByTemplateFile(from, to, "邮件标题测试1", "D:/Zhao/MyWorkspace/MailTools/src/com/ibm/tivoli/tools/sgm.mail.template.html", "GBK", properties, "c:/temp/Sales Tactics 1 - TSIEM with OMNIBus and TBSM .ppt");

    // Case#2
    String templateContent = sendmail.loadTemplateFromStream(Sendmail.class.getResourceAsStream("/com/ibm/tivoli/tools/sgm.mail.template.html"), "GBK");
    String mailContent = sendmail.merge(templateContent, properties);
    sendmail.send(from, to, "邮件标题测试2", mailContent, null);
  }

}
