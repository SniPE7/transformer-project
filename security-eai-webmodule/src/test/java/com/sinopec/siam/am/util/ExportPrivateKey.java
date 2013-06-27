/**
 * 
 */
package com.sinopec.siam.am.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import junit.framework.TestCase;

import org.bouncycastle.util.encoders.Base64Encoder;

/**
 * @author zhaodonglu
 * 
 */
public class ExportPrivateKey {
  private File keystoreFile = new File("D:/Temp/sgm/sgm_test_idp.jks");
  private String keyStoreType = "JKS";
  private char[] password = "passw0rd".toCharArray();
  private String alias = "sgm_test_idp_cred";
  private File exportedFile = new File("D:/Temp/sgm/sgm_test_idp_cred.key");;

  public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
    try {
      Key key = keystore.getKey(alias, password);
      if (key instanceof PrivateKey) {
        Certificate cert = keystore.getCertificate(alias);
        PublicKey publicKey = cert.getPublicKey();
        return new KeyPair(publicKey, (PrivateKey) key);
      }
    } catch (UnrecoverableKeyException e) {
    } catch (NoSuchAlgorithmException e) {
    } catch (KeyStoreException e) {
    }
    return null;
  }

  public void export() throws Exception {
    KeyStore keystore = KeyStore.getInstance(keyStoreType);
    Base64Encoder encoder = new Base64Encoder();
    keystore.load(new FileInputStream(keystoreFile), password);
    KeyPair keyPair = getPrivateKey(keystore, alias, password);
    PrivateKey privateKey = keyPair.getPrivate();
    byte[] keyBytes = privateKey.getEncoded();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    encoder.encode(keyBytes, 0, keyBytes.length, out);
    String encoded = new String(out.toByteArray());
    FileWriter fw = new FileWriter(exportedFile);
    fw.write("--BEGIN PRIVATE KEY--\n");
    fw.write(encoded);
    fw.write("\n");
    fw.write("--END PRIVATE KEY--");
    fw.close();
  }
  
  public static void main(String[] args) throws Exception {
	new ExportPrivateKey().export();
  }

}

