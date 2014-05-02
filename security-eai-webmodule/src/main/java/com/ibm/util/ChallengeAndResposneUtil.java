/**
 * 
 */
package com.ibm.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

/**
 * @author zhaodonglu
 * 
 */
public class ChallengeAndResposneUtil {

  private static final int LENGTH_ALPHANUMERIC_SET = "abcdefghijklmnoprstuvxyz1234567890".length();

  private String primaryDigest = "SHA-256";

  /**
   * 
   */
  public ChallengeAndResposneUtil() {
    super();
  }

  private static String generateSalt() {
    return generateSalt(12);
  }

  private static String generateSalt(int saltLength) {
    StringBuffer strGeneratedSalt = new StringBuffer();

    Random random = new Random();
    int nRandom = 0;

    for (int i = 0; i < saltLength; i++) {
      nRandom = random.nextInt() % LENGTH_ALPHANUMERIC_SET;
      if (nRandom < 0) {
        nRandom = -nRandom;
      }

      strGeneratedSalt.append("abcdefghijklmnoprstuvxyz1234567890".charAt(nRandom));
    }

    return strGeneratedSalt.toString();
  }

  public byte[] hashForUpdate(String dataToHash) {
    if ((dataToHash == null) || (dataToHash.length() == 0)) {
      return new byte[0];
    }
    try {
      return hashForUpdate(dataToHash.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("com.ibm.itim.util.EncryptionManager.UTF8_NOT_SUPPORTED");
  }

  public byte[] hashForUpdate(byte[] dataToHash) {
    if ((dataToHash == null) || (dataToHash.length == 0)) {
      return new byte[0];
    }
    return hash(dataToHash, generateSalt(), this.primaryDigest);
  }

  public byte[] hash(byte[] dataToHash, String saltStr, String digestType) {
    try {
      byte[] salt = saltStr.getBytes("UTF-8");
      byte[] saltedData = new byte[salt.length + dataToHash.length];
      int i = 0;
      for (; i < salt.length; i++) {
        saltedData[i] = salt[i];
      }

      for (int j = 0; j < dataToHash.length; j++) {
        saltedData[(i++)] = dataToHash[j];
      }

      byte[] hashResult = hash(saltedData, digestType);
      eraseBytes(saltedData);

      StringBuilder combined = new StringBuilder(digestType);
      combined.append(":");
      combined.append(base64Encode(salt));
      combined.append(":");
      combined.append(new String(hashResult, "UTF-8"));

      return combined.toString().getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("com.ibm.itim.util.EncryptionManager.UTF8_NOT_SUPPORTED");
  }

  private byte[] hash(byte[] password, String digestType) {
    if ((password == null) || (password.length == 0))
      return new byte[0];
    try {
      MessageDigest md = MessageDigest.getInstance(digestType);

      md.update(password);

      return base64Encode(md.digest()).getBytes("UTF-8");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("com.ibm.itim.util.EncryptionManager.HASH_ALGORITHM_NOT_SUPPORTED: " + digestType);
    } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("com.ibm.itim.util.EncryptionManager.UTF8_NOT_SUPPORTED");
  }

  private static void eraseBytes(byte[] bytes) {
    if (bytes != null)
      for (int i = 0; i < bytes.length; i++)
        bytes[i] = 0;
  }

  private static final String base64Encode(byte[] b) {
    BASE64Encoder encoder = new BASE64Encoder();

    return encoder.encode(b);
  }

}
