package com.ibm.cmcc.appplugin;

import java.util.Random;

public class IDGenerator {

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

}
