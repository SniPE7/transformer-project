package com.ibm.tivoli.cmcc.server.connector;

import com.ibm.tivoli.cmcc.connector.ConnectorManager;

public interface ConnectorManagerSupplier {

  public abstract ConnectorManager getConnectorManager(String artifactDomain);

}