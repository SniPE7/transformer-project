package com.ibm.tivoli.cmcc.web.tool;

import java.io.IOException;
import java.util.Properties;

import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;

public class ConfigHelper {

  /**
   * @return
   * @throws IOException
   */
  public static Properties getConfigProperties() throws IOException {
    Properties properties = new Properties();
    properties.load(ConfigHelper.class.getResourceAsStream("/saml-test-tool-config.properties"));
    return properties;
  }

  /**
   * @param properties
   * @return
   */
  public static NetworkConnectorManager getNetworkConnectorManager(Properties properties) {
    NetworkConnectorManager connectionManager = new NetworkConnectorManager();
    connectionManager.setConnectorClassName(properties.getProperty("saml.client.connector.class", SimpleNetworkConnectorImpl.class.getClass().getName()));
    connectionManager.setKeyStoreKeyPassword(properties.getProperty("saml.client.ssl.key.password", "importkey").toCharArray());
    connectionManager.setKeyStorePassword(properties.getProperty("saml.client.ssl.key.store.password", "importkey").toCharArray());
    connectionManager.setKeyStorePath(properties.getProperty("saml.client.ssl.key.store.path", ""));
    connectionManager.setProtocol(properties.getProperty("remote.saml.server.protocol", "SSL"));
    connectionManager.setServerName(properties.getProperty("remote.saml.server.hostname", "218.206.191.90"));
    connectionManager.setServerPort(Integer.parseInt(properties.getProperty("remote.saml.server.port", "8080")));
    connectionManager.setTimeOut(Integer.parseInt(properties.getProperty("remote.saml.server.timeout", "10")));
    connectionManager.setTrustCertsStorePassword(properties.getProperty("saml.client.ssl.trust.store.password", "importkey").toCharArray());
    connectionManager.setTrustCertsStorePath(properties.getProperty("saml.client.ssl.trust.store.path", ""));
    return connectionManager;
  }

}
