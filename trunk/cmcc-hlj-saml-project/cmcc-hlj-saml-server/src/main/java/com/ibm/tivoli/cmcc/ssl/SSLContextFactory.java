/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package com.ibm.tivoli.cmcc.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory to create a bougus SSLContext.
 * 
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 555855 $, $Date: 2007-07-13 12:19:00 +0900 (Fri, 13 Jul 2007)
 *          $
 */
public class SSLContextFactory {

  private static Log log = LogFactory.getLog(SSLContextFactory.class);

  private static SSLContext serverInstance = null;

  private static SSLContext clientInstance = null;

  /**
   * Get SSLContext singleton.
   * 
   * @return SSLContext
   * @throws java.security.GeneralSecurityException
   * 
   */
  public static SSLContext getServerInstance(String protocol, String keyManagerAlgorithm, InputStream keyStoreInputStream, String keyStoreType,
      char[] storePassword, char[] keyPassword) throws GeneralSecurityException {
    SSLContext retInstance = null;
    if (serverInstance == null) {
      synchronized (SSLContextFactory.class) {
        if (serverInstance == null) {
          try {
            // Read KeyStore

            // Load KetStore
            log.info(String.format("Loading SSL KeyStore, type: [%s]", keyStoreType));
            KeyStore keyStore = loadKetStore(keyStoreType, keyStoreInputStream, storePassword);

            // Init Key Manager
            log.info(String.format("Initializing Key Manager Factory, algorithm: [%s]", keyManagerAlgorithm));
            KeyManagerFactory kmf = initKeyManagerFactory(keyManagerAlgorithm, keyStore, keyPassword);

            serverInstance = createBougusServerSSLContext(protocol, keyStore, kmf);
          } catch (Exception ioe) {
            throw new GeneralSecurityException("Can't create Server SSLContext:" + ioe);
          }
        }
      }
    }
    retInstance = serverInstance;
    return retInstance;
  }

  /**
   * Get SSLContext singleton.
   * 
   * @return SSLContext
   * @throws java.security.GeneralSecurityException
   * 
   */
  public static SSLContext getClientInstance(String protocol) throws GeneralSecurityException {
    SSLContext retInstance = null;
    if (clientInstance == null) {
      synchronized (SSLContextFactory.class) {
        if (clientInstance == null) {
          clientInstance = createBougusClientSSLContext(protocol);
        }
      }
    }
    retInstance = clientInstance;
    return retInstance;
  }

  private static SSLContext createBougusServerSSLContext(String protocol, KeyStore keyStore, KeyManagerFactory kmf) throws GeneralSecurityException,
      IOException {
    // Initialize the SSLContext to work with our key managers.
    SSLContext sslContext = SSLContext.getInstance(protocol);
    sslContext.init(kmf.getKeyManagers(), TrustManagerFactory.X509_MANAGERS, null);
    return sslContext;
  }

  /**
   * @param keyManagerFactoryAlgorithm
   * @param keyStore
   * @param keyPassword
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyStoreException
   * @throws UnrecoverableKeyException
   */
  private static KeyManagerFactory initKeyManagerFactory(String keyManagerFactoryAlgorithm, KeyStore keyStore, char[] keyPassword)
      throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
    // Set up key manager factory to use our key store
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm);
    kmf.init(keyStore, keyPassword);
    return kmf;
  }

  /**
   * @param storePassword
   * @return
   * @throws KeyStoreException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws CertificateException
   */
  private static KeyStore loadKetStore(String type, InputStream keyStoreIn, char[] storePassword) throws KeyStoreException, IOException,
      NoSuchAlgorithmException, CertificateException {
    KeyStore ks = KeyStore.getInstance(type);
    try {
      ks.load(keyStoreIn, storePassword);
    } finally {
      if (keyStoreIn != null) {
        try {
          keyStoreIn.close();
        } catch (IOException ignored) {
        }
      }
    }
    return ks;
  }

  private static SSLContext createBougusClientSSLContext(String protocol) throws GeneralSecurityException {
    SSLContext context = SSLContext.getInstance(protocol);
    context.init(null, TrustManagerFactory.X509_MANAGERS, null);
    return context;
  }

}
