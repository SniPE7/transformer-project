/**
 * 
 */
package com.ibm.tivoli.cmcc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao Dong Lu
 * 
 */
public class Helper {
  private static Log log = LogFactory.getLog(Helper.class);

  /**
   * 
   */
  public Helper() {
    super();
  }

  public static String formatDate4SAML(Date now) {
    // 2007-12-05T09:27:10Z
    DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat timeFormater = new SimpleDateFormat("HH:mm:ssZ");
    String issueInstant = dateFormater.format(now) + "T" + timeFormater.format(now);
    return issueInstant;
  }

  public static String generatorID() {
    int len = 32;
    return generateID(len);
  }

  /**
   * @param len
   * @return
   */
  public static String generateID(int len) {
    char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
        '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    StringBuffer buf = new StringBuffer();
    Random radom = new Random();
    for (int i = 0; i < len; i++) {
      buf.append(chars[radom.nextInt(chars.length)]);
    }
    return buf.toString();
  }

  public static void validateArtifactID(String id) {
    if (id == null || id.length() < 32) {
      throw new RuntimeException("ArtifactID too short or empty, [" + id + "]");
    }
    char[] cs = id.toCharArray();
    for (char c : cs) {
      if (!Character.isDigit(c) && !Character.isLetter(c)) {
        throw new RuntimeException("ArtifactID contain invaludate character, [" + id + "]");
      }
    }
  }

  /**
   * 根据路径加载资源.
   * path允许使用"classpath:"或"file:", 如果未使用上述前缀, 则按照Classpath进行加载.<br/>
   * 例如: 
   * classpath:/certs/server_pwd_importkey.jks, 表示从classpath中加载相关资源<br/>
   * file:/home/saml/conf/certs/client_pwd_importkey.jks, 表示从文件系统加载相关资源.<br/>
   * /certs/saml/conf/certs/client_pwd_importkey.jks, 表示从classpath中加载相关资源.<br/>
   * 
   * @param clazz
   * @param path
   * @return
   * @throws FileNotFoundException
   */
  public static InputStream getResourceAsStream(Class clazz, String path) throws FileNotFoundException {
    if (path == null) {
      return null;
    }
    if (path.startsWith("file:")) {
      String realPath = path.substring("file:".length());
      return new FileInputStream(realPath);
    } else if (path.startsWith("classpath:")) {
      String realPath = path.substring("classpath:".length());
      return clazz.getResourceAsStream(realPath);
    } else {
      InputStream in = clazz.getResourceAsStream(path);
      return in;
    }
  }

}
