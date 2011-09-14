package com.ibm.tivoli.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Html2MimeBodyPart {

  private String charset = "UTF-8";
  private URL url = null;
  private File file = null;
  private String content = null;

  public Html2MimeBodyPart() {
    super();
  }

  public Html2MimeBodyPart(URL url) {
    super();
    this.url = url;
  }

  public Html2MimeBodyPart(File file) {
    super();
    this.file = file;
  }

  public Html2MimeBodyPart(String content) {
    super();
    this.content = content;
  }

  /**
   * @param content
   * @param charset
   */
  public Html2MimeBodyPart(String content, String charset) {
    super();
    this.content = content;
    this.charset = charset;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public MimeBodyPart getMimeBodyPart() throws IOException {
    String content = this.getContent();
    return this.convert(content);
  }

  private String getContent() throws IOException {
    if (this.content != null) {
      return this.content;
    }

    InputStream in = null;
    if (this.url != null) {
      URLConnection connection = this.url.openConnection();
      in = connection.getInputStream();
    } else {
      in = new FileInputStream(this.file);
    }
    if (in == null) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    int len = in.read(buf);
    while (len > 0) {
      out.write(buf, 0, len);
      len = in.read(buf);
    }
    in.close();
    out.close();
    return new String(out.toByteArray(), this.getCharset());
  }

  private MimeBodyPart convert(String src) {

    String text = src;
    htmlTagAttrValue[] imgs = getHTMLImages(src);

    Multipart mp = new MimeMultipart();

    try {
      MimeBodyPart[] mbp = new MimeBodyPart[imgs.length];
      for (int i = 0; i < imgs.length; i++) {
        // attach the file to the message
        String fileSource = imgs[i].getValue();
        DataSource ds = getDataSource(fileSource);

        mbp[i] = new MimeBodyPart();
        mbp[i].setDataHandler(new DataHandler(ds));
        mbp[i].setFileName(fileSource);
        mbp[i].setHeader("Content-Type", "image/jpeg");
        String cid = "part" + fileSource.hashCode();
        mbp[i].setHeader("Content-ID", "<" + cid + ">");
        mbp[i].setHeader("Content-Disposition", "inline;filename='" + fileSource + "'");

        int begin = text.indexOf(fileSource);
        int end = begin + fileSource.length();

        text = text.substring(0, begin) + "cid:" + cid + text.substring(end, text.length());
      }

      MimeBodyPart mbp0 = new MimeBodyPart();
      mbp0.setDataHandler(new DataHandler(new ByteArrayDataSource(text, "text/html", charset)));
      mbp0.setHeader("Content-Type", "text/html;charset=" + charset);

      mp.addBodyPart(mbp0);

      for (int i = 0; i < imgs.length; i++) {
        mp.addBodyPart(mbp[i]);
      }

      MimeBodyPart HTMLmbp = new MimeBodyPart();
      HTMLmbp.setContent(mp);
      String NewType = mp.getContentType();

      NewType = "multipart/related" + NewType.substring(15);
      HTMLmbp.addHeader("Content-Type", NewType);

      return HTMLmbp;
    } catch (Exception e) {
      System.out.println("Failure : " + e.toString());
    }
    return null;
  }

  /**
   * @param fileSource
   * @return
   * @throws MalformedURLException
   */
  private DataSource getDataSource(String fileSource) throws MalformedURLException {
    DataSource ds = null;
    if (this.file != null) {
      if (fileSource.startsWith("http://")) {
        ds = new URLDataSource(new URL(fileSource));
      } else {
        ds = new FileDataSource(fileSource);
      }
    } else {
      if (this.url != null) {
        ds = new URLDataSource(new URL(this.url, fileSource));
      } else {
        ds = new URLDataSource(new URL(fileSource));
      }
    }
    return ds;
  }

  private htmlTagAttrValue[] getHTMLImages(String src) {

    String text = src;
    htmlTagAttrValue[] imgs = new htmlTagAttrValue[1024];
    int count = 0;

    htmlTagAttrValue htav = null;
    htav = getTagAttrValue(text, "body", "background");
    if (null != htav) {
      imgs[count++] = htav;
    }

    htav = getTagAttrValue(text, "img", "src");
    while (null != htav) {
      imgs[count++] = htav;

      int begin = htav.getEnd();
      text = src.substring(begin, src.length());
      htav = getTagAttrValue(text, "img", "src");
      if (null == htav) {
        break;
      }
      htav.setBegin(begin + htav.getBegin());
      htav.setEnd(begin + htav.getEnd());
    }

    htmlTagAttrValue[] tmp = new htmlTagAttrValue[count];

    for (int i = 0; i < count; i++) {
      tmp[i] = imgs[i];
    }
    return tmp;
  }

  private htmlTag getTag(String text, String tagname) {
    String text_lowcase = text.toLowerCase();

    int i = text_lowcase.indexOf("<" + tagname);
    int j = text_lowcase.indexOf(">", i);

    if (-1 != i && -1 != j) {
      return new htmlTag(text.substring(i, j + 1), i, j + 1);
    } else if (-1 == i) {
      // 无法找到Tagname
      return null;
    } else {
      // 无结束符">"
      return null;
    }
  }

  private htmlTagAttr getTagAttr(String text, String tagname, String attrname) {

    htmlTag ht = getTag(text, tagname);

    if (null == ht) {
      return null;
    } else {
      String tagstr = ht.getTag();

      String tagstr_lowcase = tagstr.toLowerCase();

      int i = tagstr_lowcase.indexOf(attrname + "=");

      int j1 = tagstr_lowcase.indexOf(">", i);
      j1 = (0 > j1) ? java.lang.Integer.MAX_VALUE : j1;
      int j2 = tagstr_lowcase.indexOf(" ", i);
      j2 = (0 > j2) ? java.lang.Integer.MAX_VALUE : j2;

      int j = (j2 > j1) ? j1 : j2;

      j = (j == java.lang.Integer.MAX_VALUE) ? -1 : j;

      if (-1 != i && -1 != j) {
        return new htmlTagAttr(tagstr.substring(i, j), ht.getBegin() + i, ht.getBegin() + j);
      } else if (-1 == i) {
        // 无法找到Attribute name
        return null;
      } else {
        // 无结束符">"
        return null;
      }

    }
  }

  private htmlTagAttrValue getTagAttrValue(String text, String tagname, String attrname) {

    htmlTagAttr hta = getTagAttr(text, tagname, attrname);
    if (null != hta) {
      String tagstr = hta.getAttr();

      int i = tagstr.indexOf("=\"");
      if (-1 != i) {
        int end = tagstr.lastIndexOf('"');
        String value = tagstr.substring(i + 2, end);
        return new htmlTagAttrValue(value, hta.getBegin() + i + 2, hta.getBegin() + tagstr.length() - 1);
      } else {
        i = tagstr.indexOf("=");
        String value = tagstr.substring(i + 1, tagstr.length());
        return new htmlTagAttrValue(value, hta.getBegin() + i + 1, hta.getBegin() + tagstr.length());
      }
    } else {
      return null;
    }
  }

}

class htmlTagAttrValue {
  String attrvalue = null;

  int begin = 0;

  int end = 0;

  public htmlTagAttrValue(String attrvalue, int begin, int end) {

    this.attrvalue = attrvalue;
    this.begin = begin;
    this.end = end;

  }

  public String getValue() {
    return this.attrvalue;
  }

  public int getBegin() {
    return this.begin;
  }

  public int getEnd() {
    return this.end;
  }

  public void setBegin(int b) {
    this.begin = b;
  }

  public void setEnd(int e) {
    this.end = e;
  }
}

class htmlTagAttr {
  String attr = null;

  int begin = 0;

  int end = 0;

  public htmlTagAttr(String name, int begin, int end) {

    this.attr = name;
    this.begin = begin;
    this.end = end;

  }

  public String getAttr() {
    return this.attr;
  }

  public int getBegin() {
    return this.begin;
  }
}

class htmlTag {
  String tagstr = null;

  int begin = 0;

  int end = 0;

  public htmlTag(String tagstr, int begin, int end) {

    this.tagstr = tagstr;
    this.begin = begin;
    this.end = end;

  }

  public String getTag() {
    return this.tagstr;
  }

  public int getBegin() {
    return this.begin;
  }
}
