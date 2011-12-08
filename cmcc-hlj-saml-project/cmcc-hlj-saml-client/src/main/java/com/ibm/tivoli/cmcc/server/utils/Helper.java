/**
 * 
 */
package com.ibm.tivoli.cmcc.server.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Zhao Dong Lu
 *
 */
public class Helper {

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
    char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
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
    for (char c: cs) {
        if (!Character.isDigit(c) && !Character.isLetter(c)) {
          throw new RuntimeException("ArtifactID contain invaludate character, [" + id + "]");
        }
    }
  }

  /**
   * Search in classpath or file system
   * @return
   */
  public static InputStream getResourceAsStream(Class clazz, String path) {
    // TODO
    InputStream in = clazz.getResourceAsStream(path);
    return in;
  }

}
